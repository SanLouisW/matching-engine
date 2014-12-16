package com.davidwales.matchingengine;

import java.io.IOException;

import org.java_websocket.server.WebSocketServer;

import com.davidwales.matchingengine.inputorder.InputEvent;
import com.davidwales.matchingengine.network.NetworkInputConfiguration;
import com.davidwales.matchingengine.outputorder.OutputEvent;
import com.google.inject.Inject;
import com.lmax.disruptor.dsl.Disruptor;

public class ApplicationStarter 
{
	
	private final WebSocketServer webSocketServer;
	
	private final NetworkInputConfiguration networkInputConfiguration;
	
	private final Disruptor<InputEvent> inputDisruptor;
	
	private final Disruptor<OutputEvent> outputDisruptor;
	
	@Inject
	public ApplicationStarter(WebSocketServer webSocketServer, NetworkInputConfiguration networkInputConfiguration, Disruptor<InputEvent> inputDisruptor, Disruptor<OutputEvent> outputDisruptor)
	{
		this.webSocketServer = webSocketServer;
		this.networkInputConfiguration = networkInputConfiguration;
		this.inputDisruptor = inputDisruptor;
		this.outputDisruptor = outputDisruptor;
	}
	
	void start() throws IOException
	{
		inputDisruptor.start();
		outputDisruptor.start();
		networkInputConfiguration.bind();
		webSocketServer.start();
	}

}
