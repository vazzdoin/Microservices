package com.project.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.project.microservices.currencyconversionservice.controller.Entity.CurrencyConversion;
import com.project.microservices.currencyconversionservice.proxy.CurrencyExchangeProxy;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy ceProxy;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	private CurrencyConversion convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		
		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate()
				.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConversion.class, uriVariables);
		CurrencyConversion currConv = responseEntity.getBody();
		return new CurrencyConversion(currConv.getId(), from, to, quantity, currConv.getConversionMultiple(), 
				quantity.multiply(currConv.getConversionMultiple()), currConv.getEnvironment()+"-restTemplate");
		
	}
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	private CurrencyConversion convertCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		
		CurrencyConversion currConv = ceProxy.convertCurrency(from, to);
		return new CurrencyConversion(currConv.getId(), from, to, quantity, currConv.getConversionMultiple(), 
				quantity.multiply(currConv.getConversionMultiple()), currConv.getEnvironment()+"-feign");
		
	}
	
}
