package com.log.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import sun.nio.cs.ext.ISCII91;


public class LogTask extends TimerTask{

	@Override
	public void run() {
		CacheManager cacheManager = CacheManager.getInstance();
		Map map = cacheManager.getData("log");
		
		File file = generateLogFile();
		if(map != null && !map.isEmpty()){
			for(Iterator it = map.keySet().iterator(); it.hasNext();){
				String username = it.next().toString();
				Map dataMap = (HashMap)map.get(username);
				
				for(Iterator i = dataMap.keySet().iterator(); i.hasNext();){
					String method = i.next().toString();
					int count = (Integer)dataMap.get(method);
					//写入内存日志到文件
					FileUtil.updateFileLog(file,username, method, count);
				}
			}
		}
		//内存日志清零
		cacheManager.romoveGroup("log");
		
	}
	
	private File generateLogFile(){
		DateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
		Date today = new Date();
		File file = new File("D:\\log\\"+sdf.format(today) + "_log.txt");
		if(!file.exists()){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(today);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			Date yesterday = calendar.getTime();
			File oldFile = new File("D:\\log\\"+ sdf.format(yesterday) + "_log.txt");
			if(oldFile.exists()){
				FileUtil.copyFile(oldFile, file);
			}else{
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return file;
	}
	
	private String parseMapToString(Map map){
		String content = "";
		for(Iterator it = map.keySet().iterator(); it.hasNext();){
			String username = it.next().toString();
			
			Map dataMap = (HashMap)map.get(username);
			for(Iterator i = dataMap.keySet().iterator(); i.hasNext();){
				String method = i.next().toString();
				int count = Integer.parseInt(dataMap.get(method).toString());
				content += username + " " + method + " " + count + "\n";
			}
		}
		return content.substring(0,content.length()-1);
	}
	
	public void oldRun(){
		CacheManager cacheManager = CacheManager.getInstance();
		Map map = cacheManager.getData("log");
		String content = "";
		List logList = FileUtil.readFile(new File("D:\\log\\log.txt"));
		
		if(map != null && !map.isEmpty()){
			for(Iterator it = map.keySet().iterator(); it.hasNext();){
				String username = it.next().toString();
				
				for(Object object: logList){
					String log[] = object.toString().split(" ");
					//同一用户
					if(log[0].equals(username)){
						Map dataMap = (HashMap)map.get(username);
						boolean isExist = false;
						for(Iterator i = dataMap.keySet().iterator(); i.hasNext();){
							String method = i.next().toString();
							
							//同一用户，同一方法名
							if(log[1].equals(method)){
								int count = (Integer)dataMap.get(method) + Integer.parseInt(log[2]);
								dataMap.put(method, count);
								cacheManager.put("log", username, dataMap);
								isExist = true;
							}
						}
						if(!isExist){
							Map tempMap = new HashMap<String,String>();
							tempMap.put(log[1], log[2]);
							System.out.println("add0:"+log[0] + " " + log[1] +" "+ log[2]);
							cacheManager.put("log", username, tempMap);
						}
					}else{
						Map tempMap = new HashMap<String,String>();
						tempMap.put(log[1], log[2]);
						System.out.println("add1:"+log[0] + " " + log[1] +" "+ log[2]);
						cacheManager.put("log", log[0], tempMap);
					}
				}
			}
			
			content = parseMapToString(map);
			
			System.out.println("===================CacheLog=====================");
			System.out.println(content);
			System.out.println("================================================");
			
			File file =  new File("D:\\log\\log.txt");
			File newFile = new File("D:\\log\\log.txt.bak");
			//备份日志文件
			FileUtil.copyFile(file, newFile);
			//写入日志文件
			FileUtil.writeFile(content, file);
			
			//内存日志清零
			cacheManager.romoveGroup("log");
	}
	}

}
