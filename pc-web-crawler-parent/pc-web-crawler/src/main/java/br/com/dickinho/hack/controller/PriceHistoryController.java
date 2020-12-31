package br.com.dickinho.hack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.dickinho.hack.service.PriceHistoryService;

@RestController
public class PriceHistoryController {
	@Autowired
	PriceHistoryService priceHistoryService;
	
	@ResponseBody
	@GetMapping(value="/find/alldates")
	public ResponseEntity<?> findAllDates(){
		return new ResponseEntity<>(priceHistoryService.findAllDates(),HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value="/find/prices/{tipo}")
	public ResponseEntity<?> findPrices(@PathVariable("tipo") String tipo){
		return new ResponseEntity<>(priceHistoryService.getChartData(tipo),HttpStatus.OK);
	}
}
