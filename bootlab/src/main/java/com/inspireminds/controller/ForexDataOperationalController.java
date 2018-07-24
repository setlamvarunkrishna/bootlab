package com.inspireminds.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inspireminds.common.ExchangeValueRequest;
import com.inspireminds.common.ExchangeValueResponse;
import com.inspireminds.entity.ExchangeValue;
import com.inspireminds.repository.local.DataCacheServiceLocal;

@RestController
public class ForexDataOperationalController {

	@Autowired
	private DataCacheServiceLocal dataCacheService;

	public DataCacheServiceLocal getDataCacheService() {
		return dataCacheService;
	}

	public void setDataCacheService(DataCacheServiceLocal dataCacheService) {
		this.dataCacheService = dataCacheService;
	}

	@RequestMapping(value = "/data/add", method = RequestMethod.POST)
	public ExchangeValueResponse addOperation(@RequestBody ExchangeValueRequest request) {
		ExchangeValueResponse response = new ExchangeValueResponse();
		try {

			response.setExchangeValue(getDataCacheService().add(request.getExchangeValue()));
			response.setStatus(ExchangeValueResponse.Status.SUCCESSFULLY_COMPLETED);
			//response.setExchangeValue(request);

		} catch (Throwable t) {
			System.out.println(String.format("Unable to process your request :%s, error:%s", request, t.getMessage()));
			response.setExchangeValue(request.getExchangeValue());
			response.setStatus(ExchangeValueResponse.Status.FAILED);

		}

		return response;
	}

	@RequestMapping(value = "/data/replace", method = RequestMethod.POST)
	public ExchangeValueResponse replaceOperation(@RequestBody ExchangeValueRequest request) {
		ExchangeValueResponse response = new ExchangeValueResponse();
		try {

			response.setExchangeValue(getDataCacheService().replace(request.getExchangeValue()));
			response.setStatus(ExchangeValueResponse.Status.SUCCESSFULLY_COMPLETED);

		} catch (Throwable t) {
			System.out.println(String.format("Unable to process your request :%s, error:%s", request, t.getMessage()));
			response.setExchangeValue(request.getExchangeValue());
			response.setStatus(ExchangeValueResponse.Status.FAILED);

		}

		return response;
	}

	@RequestMapping(value = "/data/remove", method = RequestMethod.POST)
	public ExchangeValueResponse removeOperation(@RequestBody ExchangeValueRequest request) {
		ExchangeValueResponse response = new ExchangeValueResponse();
		try {

			response.setExchangeValue(getDataCacheService().remove(request.getExchangeValue()));
			response.setStatus(ExchangeValueResponse.Status.SUCCESSFULLY_COMPLETED);

		} catch (Throwable t) {
			System.out.println(String.format("Unable to process your request :%s, error:%s", request, t.getMessage()));
			response.setExchangeValue(request.getExchangeValue());
			response.setStatus(ExchangeValueResponse.Status.FAILED);

		}

		return response;
	}

	@RequestMapping(value = "/data/list", method = RequestMethod.GET)
	public List<ExchangeValue> getList() {
		try {

			return getDataCacheService().getList();

		} catch (Throwable t) {
			System.out.println(String.format("Unable to process  error:%s", t.getMessage()));

		}

		return null;
	}

}
