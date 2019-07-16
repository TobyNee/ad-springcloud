package com.imooc.ad.index.keyword;

import com.imooc.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class UnitKeywordIndex implements IndexAware<String, Set<Long>> {

	private static Map<String, Set<Long>> keywordUnitMap;
	private static Map<Long, Set<String>> unitKeywordMap;

	static {
		keywordUnitMap = new ConcurrentHashMap<>();
		unitKeywordMap = new ConcurrentHashMap<>();
	}

	@Override
	public Set<Long> get(String key) {
		if (StringUtils.isEmpty(key)) {
			return Collections.emptySet();
		}
		Set<Long> result = keywordUnitMap.get(key);
		if (result == null) {
			return Collections.emptySet();
		}
		return result;
	}

	@Override
	public void add(String key, Set<Long> value) {

	}

	@Override
	public void update(String key, Set<Long> value) {

	}

	@Override
	public void delete(String key, Set<Long> value) {

	}
}
