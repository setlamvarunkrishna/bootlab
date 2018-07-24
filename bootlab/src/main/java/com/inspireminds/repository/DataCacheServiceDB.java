package com.inspireminds.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.inspireminds.entity.ExchangeValue;

@Component
public class DataCacheServiceDB extends AbstractCache implements DataCacheService {

	private static DataCacheService cacheImpl;

	public DataCacheServiceDB() {
	}

	static {

		if (cacheImpl == null) {
			cacheImpl = new DataCacheServiceDB();
		}

	}

	public void initializeData() {

		System.out.println("********* DataCahce start Impl ***************** ");
		// TODO
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

}
