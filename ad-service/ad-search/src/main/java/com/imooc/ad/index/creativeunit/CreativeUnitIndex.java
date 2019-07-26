package com.imooc.ad.index.creativeunit;

import com.imooc.ad.index.IndexAware;
import com.imooc.ad.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Slf4j
@Component
public class CreativeUnitIndex implements IndexAware<String, CreativeUnitObject> {

	private static Map<String, CreativeUnitObject> objectMap;
	private static Map<Long, Set<Long>> creativeUnitMap;
	private static Map<Long, Set<Long>> unitCreativeMap;

	static {
		objectMap = new ConcurrentHashMap<>();
		creativeUnitMap = new ConcurrentHashMap<>();
		unitCreativeMap = new ConcurrentHashMap<>();
	}

	@Override
	public CreativeUnitObject get(String key) {
		return objectMap.get(key);
	}

	@Override
	public void add(String key, CreativeUnitObject value) {
		log.info("before add: {}", objectMap);
		objectMap.put(key, value);
		Set<Long> unitSet = creativeUnitMap.get(value.getAdIds());
		if (CollectionUtils.isEmpty(unitSet)) {
			unitSet = new ConcurrentSkipListSet<>();
			creativeUnitMap.put(value.getAdIds(), unitSet);
		}
		unitSet.add(value.getUnitId());
		Set<Long> creativeSet = creativeUnitMap.get(value.getUnitId());
		if (CollectionUtils.isEmpty(creativeSet)) {
			creativeSet = new ConcurrentSkipListSet<>();
			unitCreativeMap.put(value.getUnitId(), creativeSet);
		}
		creativeSet.add(value.getAdIds());
		log.info("after add: {}", objectMap);
	}

	@Override
	public void update(String key, CreativeUnitObject value) {
		log.error("creativeUnit index can not support update");
	}

	@Override
	public void delete(String key, CreativeUnitObject value) {
		log.info("before delete: {}", objectMap);
		objectMap.remove(key);
		Set<Long> unitSet = creativeUnitMap.get(value.getAdIds());
		if (CollectionUtils.isEmpty(unitSet)) {
			unitSet.remove(value.getUnitId());
		}
		Set<Long> creativeSet = creativeUnitMap.get(value.getUnitId());
		if (CollectionUtils.isEmpty(creativeSet)) {
			creativeSet.remove(value.getAdIds());
		}
		log.info("after delete: {}", objectMap);
	}
}
