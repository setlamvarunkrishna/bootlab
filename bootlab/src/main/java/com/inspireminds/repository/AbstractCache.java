package com.inspireminds.repository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.inspireminds.entity.ExchangeValue;

public abstract class AbstractCache {

	protected Map<Long, ExchangeValue> cache = Collections.synchronizedMap(new HashMap<Long, ExchangeValue>());
	private AtomicInteger primaryKey = new AtomicInteger(0);

	public AbstractCache() {
		initializeData();

	}

	public void initializeData() {

		System.out.println("********* DataCahce start Impl ***************** ");
		
		ExchangeValue value = new ExchangeValue(getNextPrimaryKey(), "EUR", "INR", new BigDecimal(75));
		cache.put(value.getId(), value);
		
		value = new ExchangeValue(getNextPrimaryKey(), "USD", "INR", new BigDecimal(65));
		cache.put(value.getId(), value);
		
		value = new ExchangeValue(getNextPrimaryKey(), "USD", "INR", new BigDecimal(65));
		cache.put(value.getId(), value);
		
		value = new ExchangeValue(getNextPrimaryKey(),  "AUD", "INR", new BigDecimal(45));
		cache.put(value.getId(), value);
		
		value = new ExchangeValue(getNextPrimaryKey(),  "EUR", "AUD", new BigDecimal(45));
		cache.put(value.getId(), value);
		
		System.out.println(String.format("Data : %s", cache));
	}
	
	public Map<Long, ExchangeValue> getCache() {
		return cache;
	}

	public void setCache(Map<Long, ExchangeValue> cache) {
		this.cache = cache;
	}

	protected Long getNextPrimaryKey() {
		return new Long(primaryKey.incrementAndGet());
	}
}
