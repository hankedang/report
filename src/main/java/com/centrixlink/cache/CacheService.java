package com.centrixlink.cache;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centrixlink.xreport.domain.model.Dashboard;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Dashboard Cache
 */
@Service
@SuppressWarnings("unchecked")
public class CacheService {

	@Autowired
	private Cache modelCache;

	public CacheService() { }

	/**
	 * 将数据存入缓存中
	 */
	public void putData(String sqlKey, Object data) {
		if (sqlKey == null) {
			return;
		}
		modelCache.put(new Element(sqlKey, data));
	}
	
	/**
	 * 从cache中获取数据
	 */
	public Object getData(String key) {
		Element element = modelCache.get(key);
		if (element == null) {
			return null;
		}
		return element.getObjectValue();
	}
	
	/**
	 * 从cache中获取分析模型对象
	 */
	public Dashboard getBoard(String key) {
		Element element = modelCache.get(key);
		if (element == null) {
			return null;
		}
		return (Dashboard) element.getObjectValue();
	}
	
	/**
	 * 从cache中获取数据
	 */
	public List<Map<String, Object>> getDataList(String sqlKey) {
		Element element = modelCache.get(sqlKey);
		if (element == null) {
			return null;
		}
		return (List<Map<String, Object>>) element.getObjectValue();
	}

	/**
	 * 从cache中获取数据
	 */
	public Map<String, Object> getDataMap(String sqlKey) {
		Element element = modelCache.get(sqlKey);
		if (element == null) {
			return null;
		}
		return (Map<String, Object>) element.getObjectValue();
	}
	
	public void close () {
		CacheManager.getInstance().shutdown();
	}
}
