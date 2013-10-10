package com.netty.util;

public final class TestServer {
	public static void main(String[] args) {
		NettyServer server = new NettyServer(8899);		
		//NettyClient client = new NettyClient("127.0.0.1",8899);
		
		server.start();
		//client.start();
	}
}