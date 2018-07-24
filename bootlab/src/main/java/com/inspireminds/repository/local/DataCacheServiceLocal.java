package com.inspireminds.repository.local;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.inspireminds.entity.ExchangeValue;
import com.inspireminds.repository.AbstractCache;
import com.inspireminds.repository.DataCacheService;

@Component
public class DataCacheServiceLocal extends AbstractCache implements DataCacheService {

	private static DataCacheService cacheImpl;

	public DataCacheServiceLocal() {
	}

	static {

		if (cacheImpl == null) {
			cacheImpl = new DataCacheServiceLocal();
		}

	}

	public synchronized DataCacheService getCacheIntance() {
		return cacheImpl;
	}

	@Override
	public ExchangeValue findById(Long Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExchangeValue> findByFrom(String from) {

		List<ExchangeValue> resultsList = cache.values().stream().filter(p -> p.getFrom().equals(from))
				.collect(Collectors.toList());

		if (!resultsList.isEmpty()) {
			System.out.println(String.format("FindByFromAndToValues from:%s  results:%s", from, resultsList));
			return resultsList;
		}

		return null;
	}

	@Override
	public List<ExchangeValue> findByTo(String to) {

		List<ExchangeValue> resultsList = cache.values().stream().filter(p -> p.getTo().equals(to))
				.collect(Collectors.toList());
		if (!resultsList.isEmpty()) {
			System.out.println(String.format("FindByFromAndToValues to:%s  results:%s", to, resultsList));
			return resultsList;
		}
		return null;
	}

	@Override
	public ExchangeValue findByFromAndTo(String from, String to) {

		List<ExchangeValue> filteredList = cache.values().stream().filter(p -> p.getFrom().equals(from))
				.collect(Collectors.toList());
		List<ExchangeValue> resultsList = filteredList.stream().filter(p -> p.getTo().equals(to))
				.collect(Collectors.toList());

		if (!resultsList.isEmpty()) {
			System.out.println(String.format("FindByFromAndToValues from:%s to:%s, results:%s", from, to, resultsList));
			return resultsList.get(0);
		}

		return null;
	}

	public ExchangeValue add(ExchangeValue value) {
		value.setId(this.getNextPrimaryKey());
		this.getCache().put(value.getId(), value);
		return value;
	}

	public ExchangeValue replace(ExchangeValue value) {
		this.getCache().put(value.getId(), value);
		return value;
	}

	public ExchangeValue remove(ExchangeValue value) {
		this.getCache().remove(value.getId());
		return value;
	}

	public List<ExchangeValue> getList() {
		return new ArrayList<ExchangeValue>(Collections.unmodifiableCollection(this.getCache().values()));
	}

}
