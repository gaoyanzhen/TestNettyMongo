package com.mongo.util;

public interface UserDAO {
	public String checkUser(String username,String password);
	
	public String getAdByIp(String ip,String token);
}
