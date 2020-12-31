package br.com.dickinho.hack.scheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.dickinho.hack.service.ComponentService;
import br.com.dickinho.hack.service.VideocardService;

@Component
public class PcWebCrawlerScheduler {
    	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		private static Logger logger = LoggerFactory.getLogger(PcWebCrawlerScheduler.class);
		
		@Autowired
		private ComponentService componentService;

	    //@Scheduled(cron = "0 0 */3 ? * *")
		//@Scheduled(fixedDelay=100000, initialDelay=5000)
		public void searchPcWebCrawler() {
			try {
				logger.debug("Processo pc-web-crawler iniciado:: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
				logger.debug("Iniciando busca dos componentes de PC:: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
				componentService.getComponentsCurrentPriceByUrl();
				logger.debug("Busca de componentes de PC finalizada:: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
				logger.debug("Processo pc-web-crawler finalizado:: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
			} catch (Exception e) {
				logger.error("Infelizmente, houve uma falha no processamento do scheduler:: Erro - {}", e.getMessage());
				e.printStackTrace();
			}
		}
}

