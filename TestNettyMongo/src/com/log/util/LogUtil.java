package com.log.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class LogUtil {
	/**
	 * 更新用户访问日志
	 * @param username 用户名
	 * @param command 方法名
	 */
	public static void updateLog(String username,String command){
		//计算用户访问次数
		CacheManager cacheManager = CacheManager.getInstance();
		
		Map groupMap = (Map)cacheManager.getData("log");
		
		if(groupMap == null){
			Map logMap = new HashMap();
			logMap.put(command, 1);
			cacheManager.put("log", username, logMap);
		}else{
			Map map = (Map)cacheManager.get("log", username);
			
			if(map == null || map.isEmpty()){
				Map logMap = new HashMap();
				logMap.put(command, 1);
				cacheManager.put("log", username, logMap);
			}else{ 
				if(map.get(command) == null){
					map.put(command,1);
				}else{
					int count = (Integer)map.get(command) + 1;
					map.put(command, count);
				}
				cacheManager.put("log", username, map);
			}
		}
	}
}
