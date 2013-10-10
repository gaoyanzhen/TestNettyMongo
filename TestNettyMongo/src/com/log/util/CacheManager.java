package com.log.util;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class CacheManager {
	private static CacheManager instance = null;
	private HashMap<String, Object> map = new HashMap<String, Object>();
	
	private CacheManager(){
		
	}
	
	public synchronized static CacheManager getInstance(){
		if (instance == null) {
			instance = new CacheManager();
		}
		return instance;
	}
	
	public synchronized void put(String group,String key,Object value){
		ConcurrentHashMap<String, Object> data;
		if (map.containsKey(group)) {
			data = (ConcurrentHashMap<String, Object>) map.get(group);
		}else{
			data = new ConcurrentHashMap<String, Object>();
			map.put(group, data);
		}
		data.put(key, value);
	}
	
	public Object get(String group,String key){
		return ((ConcurrentHashMap<String, Object>)map.get(group)).get(key);
	}
	
	public ConcurrentHashMap getData(String group){
		return (ConcurrentHashMap<String, Object>) map.get(group);
	}
	
	public void romoveGroup(String group){
		map.remove(group);
	}
}
