package com.netty.util;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

public class NettyServer {
	private ServerBootstrap bootstrap;
	private ChannelPipeline pipeline;
	private Channel channel;
	private InetSocketAddress localAddress;  
	
	public NettyServer(){
		bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(  
                Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		bootstrap.setOption("reuseAddress", true);  
		bootstrap.setOption("child.tcpNoDelay", true);  
		bootstrap.setOption("child.soLinger", 2);  
		
		pipeline = bootstrap.getPipeline();		
		pipeline.addFirst("decoder", new LengthFieldBasedFrameDecoder(100000000,0,4,0,4)); 
		//pipeline.addLast("encoder", new LengthFieldPrepender(2));
		
		//pipeline.addFirst("decoder", new MessageDecoder(100000000,0,4,0,4));  
		//pipeline.addLast("decoder", new StringDecoder());  			
		pipeline.addLast("encoder", new StringEncoder());		
		pipeline.addLast("servercnfactory", new NettyServerHandler()); 
		
		//设置处理客户端消息和各种消息事件的类(Handler)
		/*bootstrap.setPipelineFactory(new ChannelPipelineFactory(){
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(new LengthFieldPrepender(2),
						new LengthFieldBasedFrameDecoder(64*1024,0,2,0,2),
						new NettyServerHandler());
			}			
		});*/
	}
	public NettyServer(int port){
		this();
		localAddress = new InetSocketAddress(port);
	}
	
	public void config(int port){
		localAddress = new InetSocketAddress(port);
	}
	
	public void start() {  
		channel = bootstrap.bind(localAddress);  
        System.out.println("Server binded ...");
    } 
	
	public static void main(String[] args) {
		NettyServer server = new NettyServer(8899);		
		
		server.start();
	}
}
