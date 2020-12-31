package br.com.dickinho.hack.service;

import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.dickinho.hack.enu.TipoElemento;
import br.com.dickinho.hack.model.ECommerce;
import br.com.dickinho.hack.model.PriceHistory;
import br.com.dickinho.hack.model.Videocard;
import br.com.dickinho.hack.repository.ECommerceRepository;
import br.com.dickinho.hack.repository.PriceHistoryRepository;
import br.com.dickinho.hack.repository.VideocardRepository;

@Service
public class VideocardService {
	@Autowired
	public VideocardRepository videocardRepository;
	
	@Autowired
	public PriceHistoryRepository priceHistorydRepository;
	
	@Autowired
	public ECommerceRepository ecommerceRepository;
	
	@Value("${selenium.driverPath}")
	private String seleniumPath;
	
	private List<ECommerce> ecommerces;
	private static Logger logger = LoggerFactory.getLogger(VideocardService.class);
	private String valueTag;
	private TipoElemento tipoElemento;
	private String url;
	private String valorProduto = "";
	private Boolean isAvaliable;
	
	public void getVideocardsCurrentPriceByUrl()
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		List<Videocard> videocards = videocardRepository.findAll();

		for (Videocard videocard : videocards) {
			valorProduto = "";
			url = videocard.getLink();

			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true)
					.build();

			CloseableHttpClient httpClient = HttpClients.custom().setSSLContext(sslContext)
					.setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
			logger.debug("Conectando a " + url);
			HttpGet request = new HttpGet(url);
			request.addHeader("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
			request.addHeader("User-Agent",
					"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36");

			try {
				CloseableHttpResponse response = httpClient.execute(request);

				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String result = EntityUtils.toString(entity);
					// logger.debug(result);
					logger.debug("Código de retorno do  " + videocard.getEcommerce() + " foi "
							+ response.getStatusLine().getStatusCode());

					Document doc = Jsoup.parse(result);
					getProductPrice(response.getStatusLine().getStatusCode(), doc, videocard);

				}

			} catch (UnknownHostException | java.net.NoRouteToHostException e) {
				logger.debug("Deu merda no modem da multilaser, indicado pelo goiaba :(");
			} catch (Exception e) {
				if (e.getMessage() != null) {
					logger.error("Ocorreu o seguinte erro: " + e.getMessage() + "; da exceção tipo: "
							+ e.getClass().getName());
				} else {
					logger.error("Deu alguma merda federal, que o java nao consegui identificar! --> " + e.getClass().getName());
				}
			}

		}

	}
	
	private void getProductPrice(Integer responseCode, Document doc, Videocard videocard) {
		ecommerces = ecommerceRepository.findByName(videocard.getEcommerce());

		for (ECommerce ecom : ecommerces) {
			if (!valorProduto.isEmpty())
				break;

			valueTag = ecom.getElementTag();
			tipoElemento = TipoElemento.valueOf(ecom.getTipoElemento().toUpperCase());
			if (responseCode != 403) {
				if (tipoElemento == TipoElemento.CLASS) {
					valorProduto = doc.getElementsByClass(valueTag).text();
				} else {
					valorProduto = doc.getElementById(valueTag).text();
				}
				if (!valorProduto.isEmpty()) {
					isAvaliable = true;
					logger.debug("O valor do produto " + videocard.getModelo() + " é: " + valorProduto);
					saveCurrentPriceHistory(videocard);
					return; // implementar mais de um preço se for o caso
				} else {
					trySelenium(url, videocard);
				}
			} else {
				logger.debug("Requisição via HttpGet não suportada para o ecommerce " + videocard.getEcommerce()
						+ "; Iniciando procedimento via Selenium...");
				trySelenium(url, videocard);
			}
		}
		if(valorProduto.isEmpty()) {
			logger.debug("Produto " + videocard.getModelo() + " está indisponivel no momento/ou a página foi alterada!");
			isAvaliable = false;
			saveCurrentPriceHistory(videocard);
		}
		isAvaliable = false;
		valorProduto = "";
	}
	
	private void trySelenium(String baseUrl, Videocard videocard) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		System.setProperty("webdriver.chrome.driver", seleniumPath);
		WebDriver driver = new ChromeDriver(options);
		driver.get(baseUrl);

		try {
			if (tipoElemento == TipoElemento.CLASS) {
				valorProduto = driver.findElement(By.className(valueTag)).getText();
			} else {
				valorProduto = driver.findElement(By.id(valueTag)).getText();
			}
			
			logger.debug("O Valor do produto " + videocard.getModelo() + " é: " + valorProduto);
			isAvaliable = true;
			saveCurrentPriceHistory(videocard);
		} catch (org.openqa.selenium.NoSuchElementException ex) {
			logger.error("Elemento {} não encontrado na  página.", valueTag);
			return;
		} finally {
			driver.close();
			driver.quit();
			isAvaliable = false;
		}
	}
	
	private void saveCurrentPriceHistory(Videocard videocard) {
		DecimalFormat df = new DecimalFormat("#.00");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		PriceHistory newRecord = new PriceHistory();
		newRecord.setDataRegistro(LocalDateTime.now().format(formatter));
		newRecord.setEcommerce(videocard.getEcommerce());
		newRecord.setTipoProduto("videocard");
		newRecord.setMarca(videocard.getMarca());
		newRecord.setLink(videocard.getLink());
		newRecord.setModelo(videocard.getModelo());

		String valor;
		if(isAvaliable) {
		if (valorProduto.length() > 12) {
			valor = valorProduto.substring(0, 19);
		} else {
			valor = valorProduto;
		}

		valor = valor.replace("R$", "").replaceAll("[^0-9]", "").trim();
		if (valor.length() > 6)
			valor = valor.substring(0, 6);
		newRecord.setPreco(df.format(Integer.parseInt(valor) / 100.0));
		} else
			newRecord.setPreco("Produto indisponível");
		priceHistorydRepository.save(newRecord);
	}
}
