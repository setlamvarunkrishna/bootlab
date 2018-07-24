package com.inspireminds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inspireminds.common.ExchangeValueRequest;
import com.inspireminds.common.ExchangeValueResponse;
import com.inspireminds.service.jms.ForexJMSPubSub;

@RestController
public class JMSForexController {

	@Autowired
	private ForexJMSPubSub jmsPubSub;
	
	@Autowired
	private Environment environment;
	
	@RequestMapping("/ccsjms")
	public String doTest() {
		System.out.println(String.format("Environment is :%s", environment.getProperty("forex.url")));
		return "welcome to CCS Controller";
		
	}


	
}

