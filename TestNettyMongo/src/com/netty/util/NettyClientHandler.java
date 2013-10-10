package com.netty.util;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class NettyClientHandler extends SimpleChannelHandler{

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		System.out.println("Client connected ...");
		//e.getChannel().write("{\"command\":\"login\",\"username\":\"zhangsan\",\"password\":\"123\"}");
		//e.getChannel().write("{\"command\":\"getAdByIp\",\"ip\":\"202.192.3.21\",\"token\":\"SDAIWEJK\"}");
		String str = "C0000014BT000005201309060002_203|0|18916130745|null|210906212420546\n" +
					"C0000014BT000005201309040001_44|0|13371896685|null|210906171305925\n" +
					"C0000014BT000005201309040001_38|0|13371896449|null|210906171305924\n" +
					"C0000014BT000005201309040001_36|0|13371896440|null|210906171305923\n" +
					"C0000014BT000005201309040001_29|1|13371896213|0|210906171305922\n" +
					"C0000014BT000005201309040001_149|1|18001977777|0|210906171305933\n" +
					"C0000014BT000006201309040001_8077|1|18939851908|0|220906172554456\n" +
					"C0000014BT000006201309040001_8085|1|13391238026|0|220906172554457\n" +
					"C0000014BT000006201309040001_4561|1|18939939331|0|220906172554313\n" +
					"C0000014BT000003201309040012_1608|1|15307210398|0|210906172810334\n" +
					"C0000014BT000006201309040001_4708|1|13391361209|0|220906172554318\n" +
					"C0000014BT000006201309040001_4686|1|18916981008|0|220906172554317\n" +
					"C0000014BT000006201309040001_8119|1|18964308495|0|220906172554459\n" +
					"C0000014BT000006201309040001_8276|1|13371966361|0|220906172554466\n" +
					"C0000014BT000006201309040001_8143|0|13391232509|null|220906172554461\n" +
					"C0000014BT000006201309040001_4774|1|18916173677|0|220906172554321\n" +
					"C0000014BT000006201309040001_4833|0|18916193669|null|220906172554322\n" +
					"C0000014BT000006201309040001_4845|1|18964047471|0|220906172554324\n" +
					"C0000014BT000006201309040001_4853|1|13391011951|0|220906172554325\n" +
					"C0000014BT000006201309040001_4904|1|13391338875|0|220906172554329\n" +
					"C0000014BT000006201309040001_5316|1|13391116075|0|220906172554339\n" +
					"C0000014BT000006201309040001_5531|1|18917633893|0|220906172554345\n" +
					"C0000014BT000006201309040001_5340|1|13391001696|0|220906172554341\n" +
					"C0000014BT000006201309040001_9852|0|18917737866|null|220906172554551\n" +
					"C0000014BT000006201309040001_9953|1|18930330165|0|220906172554555\n" +
					"C0000014BT000006201309040001_9970|0|18917088928|null|220906172554556\n" +
					"C0000014BT000006201309040001_9949|1|13391216603|0|220906172554554\n" +
					"C0000014BT000006201309040001_9770|1|18964817646|0|220906172554548\n" +
					"C0000014BT000006201309040001_5697|0|18964990379|null|220906172554351";
		
		ChannelBuffer buffer = ChannelBuffers.buffer(str.getBytes().length+4);
		buffer.writeInt(str.getBytes().length);
		buffer.writeBytes(str.getBytes());
		
		e.getChannel().write(buffer);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		//super.exceptionCaught(ctx, e);
		System.out.println("ExceptionCause:" + e.getCause());
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		//super.messageReceived(ctx, e);
		System.out.println("Client received message:" + e.getMessage());
		/*ChannelBuffer buffer = (ChannelBuffer)e.getMessage();
    	System.out.println("Receive:"+buffer.toString(Charset.defaultCharset()));*/
	}
	
}
