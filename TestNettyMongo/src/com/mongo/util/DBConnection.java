package com.mongo.util;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class DBConnection {
	private String url = "127.0.0.1";
	private int port = 27017;
	private ServerAddress serverAddress;
	private MongoClient mongoClient;
	private DB db;
	
	public DBConnection(){
		try {
			serverAddress = new ServerAddress(url,port);
			mongoClient = new MongoClient(serverAddress);
		} catch (UnknownHostException e) {
			System.out.println("mongodb连接失败！");
			e.printStackTrace();
		}		
	}
	
	public DBConnection(String url, int port){
		try {
			serverAddress = new ServerAddress(url,port);
			mongoClient = new MongoClient(serverAddress);
		} catch (UnknownHostException e) {
			System.out.println("mongodb连接失败！");
			e.printStackTrace();
		}		
	}
	
	public void config(String url, int port){
		this.url = url;
		this.port = port;
	}
	
	public DB getDB(String dbName){
		return mongoClient.getDB(dbName);
	}
	
	public void close(){
		mongoClient.close();
	}
	
	public void dropDB(String dbName){
		mongoClient.dropDatabase(dbName);
	}
}
