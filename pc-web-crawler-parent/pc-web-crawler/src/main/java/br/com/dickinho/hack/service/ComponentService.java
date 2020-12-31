package br.com.dickinho.hack.service;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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

import br.com.dickinho.hack.enu.TipoComponente;
import br.com.dickinho.hack.enu.TipoElemento;
import br.com.dickinho.hack.model.ComponentModel;
import br.com.dickinho.hack.model.ECommerce;
import br.com.dickinho.hack.model.PriceHistory;
import br.com.dickinho.hack.repository.CaseRepository;
import br.com.dickinho.hack.repository.CoolerRepository;
import br.com.dickinho.hack.repository.ECommerceRepository;
import br.com.dickinho.hack.repository.FontRepository;
import br.com.dickinho.hack.repository.HdRepository;
import br.com.dickinho.hack.repository.MemoryRepository;
import br.com.dickinho.hack.repository.MotherboardRepository;
import br.com.dickinho.hack.repository.PriceHistoryRepository;
import br.com.dickinho.hack.repository.ProcessorRepository;
import br.com.dickinho.hack.repository.SsdRepository;
import br.com.dickinho.hack.repository.VideocardRepository;

@Service
public class ComponentService {
	@Autowired
	public SsdRepository ssdRepository;

	@Autowired
	public ProcessorRepository processorRepository;

	@Autowired
	public CaseRepository caseRepository;

	@Autowired
	public CoolerRepository coolerRepository;

	@Autowired
	public FontRepository fontRepository;

	@Autowired
	public HdRepository hdRepository;

	@Autowired
	public MemoryRepository memoryRepository;

	@Autowired
	public VideocardRepository videocardRepository;

	@Autowired
	public MotherboardRepository motherboardRepository;

	@Autowired
	public PriceHistoryRepository priceHistorydRepository;

	@Autowired
	public ECommerceRepository ecommerceRepository;

	@Value("${selenium.driverPath}")
	private String seleniumPath;

	private List<ECommerce> ecommerces;
	private static Logger logger = LoggerFactory.getLogger(ComponentService.class);
	private String valueTag;
	private TipoElemento tipoElemento;
	private String url;
	private String valorProduto = "";
	private Boolean isAvaliable;
	List<ComponentModel> pcComponents;
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	private static Integer MAX_VALUE_STRING_LENGTH = 19;
	private Boolean connectionError;
	private Boolean isValidProductValue;
	private Boolean isConnected;

	public ComponentService() {
		isValidProductValue = false;
		isConnected = false;
	}

	private void tryToConnect(ComponentModel component) throws InterruptedException {
		try {
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

			CloseableHttpResponse response = httpClient.execute(request);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				// logger.debug(result);
				logger.debug("Código de retorno do  " + component.getEcommerce() + " foi "
						+ response.getStatusLine().getStatusCode());

				Document doc = Jsoup.parse(result);
				getProductPrice(response.getStatusLine().getStatusCode(), doc, component);
			}
			connectionError = false;
		} catch (UnknownHostException | java.net.NoRouteToHostException ex) {
			connectionError = true;
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			if (e.getMessage() != null) {
				logger.error(
						"Ocorreu o seguinte erro: " + e.getMessage() + "; da exceção tipo: " + e.getClass().getName());
			} else {
				logger.error("Erro não identificado na consulta ao ecommerce " + component.getEcommerce());
			}
			connectionError = false;
		}
	}

	private void process()
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, InterruptedException {
		for (ComponentModel component : pcComponents) {
			valorProduto = "";
			url = component.getLink();

			tryToConnect(component);

			if (connectionError) {
				Integer retryCounter = 1;
				logger.debug("Falha no processo de conexao a :( " + component.getEcommerce());

				do {
					logger.debug("Tentando conectar-se novamente a : " + url);
					logger.debug("Tentativa " + retryCounter + " de conexao");
					tryToConnect(component);
					retryCounter++;
					Thread.sleep(5200);
				} while (connectionError);
			}

		}
		pcComponents.clear();
	}

	private void executeComponentSearch(Integer value) {
		TipoComponente componente = TipoComponente.getComponentByValue(value);

		if (componente == TipoComponente.MOTHERBOARD) {
			logger.debug("Iniciando busca de placas-mãe:: Execution Time - {}",
					dateTimeFormatter.format(LocalDateTime.now()));
			pcComponents = motherboardRepository.findAll().stream().collect(Collectors.toList());
		} else if (componente == TipoComponente.VIDEOCARD) {
			logger.debug("Iniciando busca de placas de vídeo:: Execution Time - {}",
					dateTimeFormatter.format(LocalDateTime.now()));
			pcComponents = videocardRepository.findAll().stream().collect(Collectors.toList());
		} else if (componente == TipoComponente.PROCESSOR) {
			logger.debug("Iniciando busca de processadores:: Execution Time - {}",
					dateTimeFormatter.format(LocalDateTime.now()));
			pcComponents = processorRepository.findAll().stream().collect(Collectors.toList());
		} else if (componente == TipoComponente.COOLER) {
			logger.debug("Iniciando busca de coolers:: Execution Time - {}",
					dateTimeFormatter.format(LocalDateTime.now()));
			pcComponents = coolerRepository.findAll().stream().collect(Collectors.toList());
		} else if (componente == TipoComponente.MEMORY) {
			logger.debug("Iniciando busca de memórias:: Execution Time - {}",
					dateTimeFormatter.format(LocalDateTime.now()));
			pcComponents = memoryRepository.findAll().stream().collect(Collectors.toList());
		} else if (componente == TipoComponente.FONT) {
			logger.debug("Iniciando busca de fontes:: Execution Time - {}",
					dateTimeFormatter.format(LocalDateTime.now()));
			pcComponents = fontRepository.findAll().stream().collect(Collectors.toList());
		} else if (componente == TipoComponente.CASE) {
			logger.debug("Iniciando busca de gabinetes:: Execution Time - {}",
					dateTimeFormatter.format(LocalDateTime.now()));
			pcComponents = caseRepository.findAll().stream().collect(Collectors.toList());
		} else if (componente == TipoComponente.SSD) {
			logger.debug("Iniciando busca de SSD:: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
			pcComponents = ssdRepository.findAll().stream().collect(Collectors.toList());
		} else if (componente == TipoComponente.HD) {
			logger.debug("Iniciando busca de HD:: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
			pcComponents = hdRepository.findAll().stream().collect(Collectors.toList());
		}
	}

	public void searchComponentPrice(Integer value)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, InterruptedException {
		executeComponentSearch(value);
		process();
	}

	public void getComponentsCurrentPriceByUrl()
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, InterruptedException {
		for (Integer i = 1; i < 10; i++) {
			isAvaliable = false;
			executeComponentSearch(i);
			process();
		}

	}

	private void saveCurrentPriceHistory(ComponentModel componentModel) throws InterruptedException {
		DecimalFormat df = new DecimalFormat("#.00");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		PriceHistory newRecord = new PriceHistory();
		newRecord.setDataRegistro(LocalDate.now().format(formatter));
		newRecord.setEcommerce(componentModel.getEcommerce());
		String nomeProduto = componentModel.getClass().getName();
		newRecord.setTipoProduto(nomeProduto.substring(27, (nomeProduto.length())).toLowerCase());
		newRecord.setMarca(componentModel.getMarca());
		newRecord.setLink(componentModel.getLink());
		newRecord.setModelo(componentModel.getModelo());

		String valor = "";
		if (isAvaliable && !valorProduto.equals("not_found")) {
			if (valorProduto.length() > 12) {
				valor = extractProductValueFromString(valor);
			} else {
				valor = valorProduto;
			}
			isValidProductValue = false;
			valor = valor.replace("R$", "").replaceAll("[^0-9]", "").trim();
			if (valor.length() > 6)
				valor = valor.substring(0, 6);
			newRecord.setPreco(df.format(Double.parseDouble(valor) / 100.0));
		} else
			newRecord.setPreco("Produto indisponível");
		priceHistorydRepository.save(newRecord);
	}

	private String extractProductValueFromString(String valor) throws InterruptedException {
		//dfrfThread.sleep(4fju0\az\z00);
		do {
			try {
				valorProduto.substring(0, MAX_VALUE_STRING_LENGTH);
				isValidProductValue = true;
				return valorProduto.substring(0, MAX_VALUE_STRING_LENGTH);
			} catch (IndexOutOfBoundsException e) {
				MAX_VALUE_STRING_LENGTH--;
			}
		} while (!isValidProductValue);
		return "";
	}

	private void getProductPrice(Integer responseCode, Document doc, ComponentModel componentModel)
			throws InterruptedException {
		ecommerces = ecommerceRepository.findByName(componentModel.getEcommerce());

		for (ECommerce ecom : ecommerces) {
			if (!valorProduto.isEmpty())
				break;

			valueTag = ecom.getElementTag();
			tipoElemento = TipoElemento.valueOf(ecom.getTipoElemento().toUpperCase());
			if (responseCode != 403) {
				if (componentModel.getEcommerce().equals("pichau")) {
					if (!doc.getElementsByClass("unavailable").text().isEmpty()) {
						isAvaliable = false;
						saveCurrentPriceHistory(componentModel);
						return;
					}
				}

				setValorProduto(doc);

				if (!valorProduto.isEmpty()) {
					isAvaliable = true;
					logger.debug("O valor do produto " + componentModel.getModelo() + " é: " + valorProduto);
					saveCurrentPriceHistory(componentModel);
					return; // implementar mais de um preço se for o caso
				} else {
					trySelenium(url, componentModel);
				}
			} else {
				logger.debug("Requisição via HttpGet não suportada para o ecommerce " + componentModel.getEcommerce()
						+ "; Iniciando procedimento via Selenium...");
				trySelenium(url, componentModel);
			}
		}
		if (valorProduto.isEmpty()) {
			logger.debug("Produto " + componentModel.getModelo()
					+ " está indisponivel no momento/ou a página foi alterada!");
			isAvaliable = false;
			saveCurrentPriceHistory(componentModel);
		}
		isAvaliable = false;
		valorProduto = "";
	}

	private void trySelenium(String baseUrl, ComponentModel componentModel) throws InterruptedException {
		WebDriver driver = null;
		do {
			try {
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--start-maximized");
				System.setProperty("webdriver.chrome.driver", seleniumPath);
				driver = new ChromeDriver(options);
				driver.get(baseUrl);
				isConnected = true;
				logger.debug("Conexao via selenium, realizada com sucesso...");
			} catch (org.openqa.selenium.WebDriverException e) {
				logger.debug("Falha ao conectar-se via selenium...");
				logger.debug("Tentando nova conexao via selenium...");
				driver.close();
				driver.quit();
				isAvaliable = false;
				Thread.sleep(10000);
			}
		} while (!isConnected);

		try {
			if (tipoElemento == TipoElemento.CLASS) {
				valorProduto = driver.findElement(By.className(valueTag)).getText();
			} else {
				valorProduto = driver.findElement(By.id(valueTag)).getText();
			}

			logger.debug("O Valor do produto " + componentModel.getModelo() + " é: " + valorProduto);
			isAvaliable = true;
			saveCurrentPriceHistory(componentModel);
		} catch (org.openqa.selenium.NoSuchElementException ex) {
			logger.error("Elemento {} não encontrado na  página.", valueTag);
			return;
		} finally {
			driver.close();
			driver.quit();
			isAvaliable = false;
			isConnected = false;
		}
	}

	private void setValorProduto(Document doc) {
		try {
			if (tipoElemento == TipoElemento.CLASS) {
				valorProduto = doc.getElementsByClass(valueTag).text();
			} else {
				valorProduto = doc.getElementById(valueTag).text();
			}
		} catch (NullPointerException e) {
			valorProduto = "not_found";
		}
	}

	public List<PriceHistory> getComponentHistoryPrice(String tipo) {
		return priceHistorydRepository.findByName(tipo);
	}

	/*
	 * public void tryToLoginElo7() { ChromeOptions options = new ChromeOptions();
	 * options.addArguments("--start-maximized");
	 * System.setProperty("webdriver.chrome.driver", "/opt/projetos/chromedriver");
	 * WebDriver driver = new ChromeDriver(options);
	 * driver.get("https://www.elo7.com.br/");
	 * 
	 * driver.findElement(By.id("login-link")).click();
	 * 
	 * driver.findElement(By.name("email")).sendKeys("bruce.maisarte@gmail.com"); ;
	 * driver.findElement(By.name("password")).sendKeys("nb051115");
	 * 
	 * driver.findElement(By.name("password")).sendKeys(Keys.RETURN);
	 * 
	 * driver.get("https://www.elo7.com.br/t7/conversations");
	 * 
	 * //driver.findElement(By.id("conversation-summary-0")).click();
	 * 
	 * //driver.findElement(By.xpath("//input[@type='file']")).sendKeys(
	 * "/home/dick/Pictures/bruce_mais_arte.jpg");;
	 * 
	 * //driver.findElement(By.id("message.message")).
	 * sendKeys("Olá, tudo bem? Me chama para finalizarmos o pedido");
	 * 
	 * try { long lastHeight = (long) ((JavascriptExecutor)
	 * driver).executeScript("return document.body.scrollHeight");
	 * 
	 * while (true) { ((JavascriptExecutor)
	 * driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
	 * Thread.sleep(2000);
	 * 
	 * long newHeight = (long) ((JavascriptExecutor)
	 * driver).executeScript("return document.body.scrollHeight"); if (newHeight ==
	 * lastHeight) { break; } lastHeight = newHeight; } } catch
	 * (InterruptedException e) { e.printStackTrace(); }
	 * 
	 * 
	 * }
	 */
}
