package com.inspireminds.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inspireminds.entity.ExchangeValue;
import com.inspireminds.repository.local.DataCacheServiceLocal;

@RestController
public class ForexController {

	// @Autowired
	// private Environment environment;

	// @Autowired
	// private ExchangeValueRepository repository;

	@Autowired
	private DataCacheServiceLocal dataCacheService;

	static {
		System.out.println("ForexController static ..... ");
	}

	@GetMapping("/currency-exchage/from/{from}/to/{to}")
	public ExchangeValue retriveExchangeValue(@PathVariable String from, @PathVariable String to) {
		System.out.println(String.format("Input from:%s to:%s", from, to));

		ExchangeValue rtnValue = null;
		try {
			rtnValue = dataCacheService.findByFromAndTo(from, to);
		} catch (Throwable t) {
			System.out.println(String.format("error%s cause%s", t.getMessage(), t.getCause()));
		}
		return rtnValue;
	}

	@GetMapping("/currency-exchage/from/{from}")
	public List<ExchangeValue> retriveExchangeValueFrom(@PathVariable String from) {
		System.out.println(String.format("Input from:%s", from));

		List<ExchangeValue> rtnValueList = null;
		try {
			rtnValueList = dataCacheService.findByFrom(from);
		} catch (Throwable t) {
			System.out.println(String.format("error%s cause%s", t.getMessage(), t.getCause()));
		}
		return rtnValueList;
	}

	@GetMapping("/currency-exchage/to/{to}")
	public List<ExchangeValue> retriveExchangeValueTo(@PathVariable String to) {
		System.out.println(String.format("Input to:%s", to));

		List<ExchangeValue> rtnValueList = null;
		try {
			rtnValueList = dataCacheService.findByTo(to);
		} catch (Throwable t) {
			System.out.println(String.format("error%s cause%s", t.getMessage(), t.getCause()));
		}
		return rtnValueList;
	}
	@RequestMapping("/forex")
	public String doTest() {

		return "Welcome to Forex Controller";
	}
}
