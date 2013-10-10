package com.mongo.util;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class UserDAOImpl implements UserDAO {

	@Override
	public String checkUser(String username, String password) {
		BasicDBObject queryObject = new BasicDBObject();
		queryObject.append("username", username).append("password", password);
		return null;
	}

	@Override
	public String getAdByIp(String ip, String token) {
		// TODO Auto-generated method stub
		return null;
	}

}
