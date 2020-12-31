package br.com.dickinho.hack;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class Application {
	
	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		SpringApplication.run(Application.class,args);
		/*ApplicationContext context = SpringApplication.run(Application.class,args);
		PcCrawlerService crawler = (PcCrawlerService)context.getBean("pcCrawlerService");
		crawler.getMotherboardsCurrentPriceByUrl();*/
		//crawler.tryToLoginElo7();
	}
}
