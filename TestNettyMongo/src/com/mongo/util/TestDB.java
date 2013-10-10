package com.mongo.util;

import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class TestDB {
	public static void main(String[] args) {
		// 连接本地数据库
		DBConnection connection = new DBConnection();	
		
		//先清除数据库内容
		connection.dropDB("new_test_db");
		
		// 创建名为new_test_db的数据库  
		DB db = connection.getDB("new_test_db");
		
		// 获取new_test_db中的集合（类似于获取关系数据库中的表）  
		Set<String> collections = db.getCollectionNames();
		for(String coll:collections){
			System.out.println(coll);
		}
		
		// 创建一个叫做"new_test_col"的集合
		DBCollection collection = db.getCollection("new_test_col");
		// 初始化一个基本DB对象，最终插入数据库的就是这个DB对象
		BasicDBObject object = new BasicDBObject();
		object.put("username", "zhangsan");
		object.put("password", "123");
		object.put("token", "SDAIWEJK");
		object.put("privilege", "getAdById");
		object.put("privilege", "disconnect");
		
		//插入对象
		collection.insert(object);
		//查看一条记录，findOne()=find().limit(1);  
		//DBObject dbObject = collection.findOne();
		//System.out.println(dbObject.get("token"));
		
		object = new BasicDBObject();
		object.put("ip", "202.192.3.21");
		object.put("token", "SDAIWEJK");
		object.put("ad", "AD5930234");
		collection.insert(object);
		
		/*for(int i=0;i<9;i++){
			collection.insert(new BasicDBObject().append("ranking", i));
		}*/
		System.out.println("数据库集合数据数："+collection.count());
		//下面我们来遍历集合，find()方法返回的是一个游标(cursor)，这里的概念和关系数据库很相似  
		DBCursor cursor = collection.find();
		while(cursor.hasNext()){
			System.out.println(cursor.next());
		}
		
		//下面来看一些略复杂一点的查询技巧，第一个，简单的条件查询，查询ranking为1的记录
		/*BasicDBObject queryObect = new BasicDBObject();
		queryObect.put("ranking", 1);
		cursor = collection.find(queryObect);
		while(cursor.hasNext()){
			System.out.println(cursor.next());
		}*/
		
		//下面是更复杂的条件查询，查询ranking大于5小于9的记录  
		/*queryObect = new BasicDBObject();
		queryObect.put("ranking", new BasicDBObject("$gt",5).append("$lt", 9));
		cursor = collection.find(queryObect);
		while(cursor.hasNext()){
			System.out.println(cursor.next());
		}*/
		
		//最后删除我们的测试数据库 
		//connection.dropDB("new_test_db");
		connection.close();
	}
}
