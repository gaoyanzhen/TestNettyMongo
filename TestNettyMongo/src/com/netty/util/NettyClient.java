package com.netty.util;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import javax.xml.soap.MessageFactory;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

public class NettyClient {
	private ClientBootstrap bootstrap;
	private ChannelPipeline pipeline;
	private ChannelPipelineFactory pipelineFactory;
	private ChannelFuture future;
	private Channel channel;
	private InetSocketAddress localAddress;  
	
	public NettyClient(){
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory( 
	                    Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		pipeline = bootstrap.getPipeline();
		//pipeline.addLast("decoder", new LengthFieldBasedFrameDecoder(64*1024,0,4,0,4));
		pipeline.addLast("decoder", new StringDecoder());  
		//pipeline.addLast("encoder", new StringEncoder());  
		pipeline.addLast("handler", new NettyClientHandler());  
		
		/*bootstrap.setPipelineFactory(new ChannelPipelineFactory() {  
			public ChannelPipeline getPipeline() {  
				ChannelPipeline pipeline =  Channels.pipeline(new LengthFieldPrepender(2),new LengthFieldBasedFrameDecoder(64*1024,0,2,0,2),new NettyClientHandler());  
				return pipeline;  
			}  
		});*/

	}
	public NettyClient(String host, int port){
		this();
		localAddress = new InetSocketAddress(host,port);
	}
	
	public void config(String host, int port){
		localAddress = new InetSocketAddress(host,port);
	}
	
	public void start(){
		future = bootstrap.connect(localAddress);
		future.getChannel().getCloseFuture().awaitUninterruptibly();  
		bootstrap.releaseExternalResources();  

	}
	
	public static void main(String[] args) {
		NettyClient client = new NettyClient("127.0.0.1",8899);
		
		client.start();
	}
}
