package com.netty.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import net.sf.json.JSONObject;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.mongo.util.DBConnection;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class NettyServerHandler extends SimpleChannelHandler{

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		//super.channelClosed(ctx, e);
		System.out.println("ServerChannel closed ...");
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		//super.channelConnected(ctx, e);
		System.out.println("ServerChannel connected ...");
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		ChannelBuffer buffer = (ChannelBuffer)e.getMessage();
    	System.out.println("Receive:"+buffer.toString(Charset.defaultCharset()));
    	//String msg = buffer.toString(Charset.defaultCharset()) + "has been processed!";
    	String msg = "has been processed!";
    	/*ChannelBuffer buffer2 = ChannelBuffers.buffer(msg.length()+4);
    	buffer2.writeInt(msg.length());
    	buffer2.writeBytes(msg.getBytes());*/
    	e.getChannel().write(msg);
		//processMessage(e);
	}
	
	private void processMessage(MessageEvent e) throws UnsupportedEncodingException {
		
		System.out.println("Server received message:" + e.getMessage());
		JSONObject json = JSONObject.fromObject(e.getMessage());
		String command = json.getString("command");
		
		//数据库连接
		DBConnection connection = new DBConnection();
		DB db = connection.getDB("new_test_db");
		DBCollection collection = db.getCollection("new_test_col");
		
		//输出命令
		System.out.println("Command:"+command);
		if(command.equals("login")){
			String result = "";
			String username = json.getString("username");
			String password = json.getString("password");
			
			BasicDBObject object = new BasicDBObject("username",username).append("password", password);
			DBObject dbObject = collection.findOne(object);
			
			if(dbObject != null){
				result = "{\"success\":\"ok\",\"message\":\"\",\"token\":\"" + (String)dbObject.get("token") +"\",\"privilege\":[{\"command\":\"getAdByIp\"},{\"command\":\"disconnect\"}]}";
			}else{
				result = "{\"success\":\"fail\",\"message\":\"用户名或密码错误！\",\"token\":\"\",\"privilege\":\"\"}";
			}		
			//System.out.println(new ChannelBuffer(result));
			e.getChannel().write(result);
		}else if(command.equals("getAdByIp")){
			String result = "";
			String ip = json.getString("ip");
			String token = json.getString("token");
			
			BasicDBObject object = new BasicDBObject("ip",ip).append("token", token);
			DBObject dbObject = collection.findOne(object);
			if(dbObject != null){
				String ad = (String)dbObject.get("ad");
				result = "{\"ad\":\""+ad+"\"}";
			}else{
				result = "{\"ad\":\"查询失败！\"}";
			}
			e.getChannel().write(result);
			
		}else if(command.equals("disconnect")){
			e.getChannel().disconnect();
		}		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		System.out.println("Exception:" + e.getCause());
	}
	
}
