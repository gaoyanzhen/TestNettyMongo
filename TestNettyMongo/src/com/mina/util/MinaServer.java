package com.mina.util;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaServer {
	private IoAcceptor ioAcceptor;
	private IoSessionConfig ioSessionConfig;
	
	public MinaServer() {
		ioAcceptor = new NioSocketAcceptor();
		ioSessionConfig = ioAcceptor.getSessionConfig();
		ioSessionConfig.setReadBufferSize(2048);
		ioSessionConfig.setIdleTime(IdleStatus.BOTH_IDLE, 300);
	}
}
