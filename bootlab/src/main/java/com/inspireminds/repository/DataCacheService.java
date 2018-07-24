package com.inspireminds.repository;

import java.util.List;

import com.inspireminds.entity.ExchangeValue;

public interface DataCacheService {

	public ExchangeValue findById(Long Id);

	public List<ExchangeValue> findByFrom(String from);

	public List<ExchangeValue> findByTo(String to);

	public ExchangeValue findByFromAndTo(String from, String to);

	default public void load() {
		// No default impl
	}

	default public void reload() {
		// No default Impl
	}

	default public ExchangeValue add(ExchangeValue value) {
		// No default Impl
		return null;
	}

	default public ExchangeValue remove(ExchangeValue value) {
		// No default Impl
		return null;
	}

	default public ExchangeValue replace(ExchangeValue value) {
		// No default Impl
		return null;
	}

	default List<ExchangeValue> getList() {
		return null;
	}
}
