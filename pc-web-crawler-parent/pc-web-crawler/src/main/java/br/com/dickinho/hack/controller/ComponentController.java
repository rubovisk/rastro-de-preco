package br.com.dickinho.hack.controller;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.dickinho.hack.enu.TipoComponente;
import br.com.dickinho.hack.service.ComponentService;

@RestController
public class ComponentController {
	@Autowired
	private ComponentService componentService;
	
	@ResponseBody
	@GetMapping(value="/component/{tipo}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getComponentByType(@PathVariable("tipo") String tipo){
		return new ResponseEntity<>(componentService.getComponentHistoryPrice(tipo),HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value="/search/{component}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> executeComponentSearch(@PathVariable("component") Integer value) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, InterruptedException{
		componentService.searchComponentPrice(value);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
