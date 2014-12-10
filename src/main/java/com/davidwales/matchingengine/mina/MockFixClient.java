package com.davidwales.matchingengine.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;


public class MockFixClient 
{
	private static final int PORT = 9123;
		
	public static void main(String[] args) throws IOException, InterruptedException
	{
		IoConnector connector = new NioSocketConnector();
		connector.getSessionConfig().setReadBufferSize(2048);
			 
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		connector.setHandler(new FixClientHandler("CLI=mockClient"));
		ConnectFuture future = connector.connect(new InetSocketAddress("localhost", PORT));
		future.awaitUninterruptibly();
		 
		if (!future.isConnected())
		{
			return;
		}
		
		IoSession session = future.getSession();
		session.write("44=10|55=aaa|54=1|38=10|109=mockClient|");
		session.getConfig().setUseReadOperation(true);
		session.getCloseFuture().awaitUninterruptibly();
		System.out.println("After Writing");
		connector.dispose();
	}
}
