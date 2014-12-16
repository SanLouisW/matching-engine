package com.davidwales.matchingengine.network;

import java.io.IOException;
import java.net.SocketAddress;

import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;

import com.google.inject.Inject;

public class NetworkInputConfigurationImpl implements NetworkInputConfiguration
{
	
	private IoAcceptor acceptor;
	
	private IoHandler handler;
	
	private IoFilterAdapter filter;
	
	private SocketAddress socketAddress;
	
	private int bufferSize;
	
	@Inject
	public NetworkInputConfigurationImpl(IoAcceptor acceptor, IoHandler handler, IoFilterAdapter filter, SocketAddress socketAddress)
	{
		this.acceptor = acceptor;
		this.handler = handler;
		this.filter = filter;
		this.socketAddress = socketAddress;
		this.bufferSize = 2048;
	}
	
	public void bind() throws IOException
	{
		acceptor.setHandler(handler);
		acceptor.getFilterChain().addLast( "codec", filter);
		acceptor.getSessionConfig().setReadBufferSize( bufferSize );
		acceptor.bind( socketAddress );	  
	}
	
}
