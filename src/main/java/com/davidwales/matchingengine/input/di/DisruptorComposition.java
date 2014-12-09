package com.davidwales.matchingengine.input.di;

import org.apache.mina.core.service.IoAcceptor;
import org.java_websocket.server.WebSocketServer;

import com.davidwales.matchingengine.input.event.DisruptorProducer;
import com.davidwales.matchingengine.input.event.IncomingOrderEvent;
import com.davidwales.matchingengine.output.disruptor.handlers.AggregatorServer;
import com.google.inject.Inject;
import com.lmax.disruptor.dsl.Disruptor;

public class DisruptorComposition 
{
	
	private final Disruptor<IncomingOrderEvent> inputDisruptor;
	
	private final DisruptorProducer<IncomingOrderEvent, byte[]> producer;
	
	private final WebSocketServer webSocketServer;
	
	private final IoAcceptor ioAcceptor;
	
	@Inject
	public DisruptorComposition(Disruptor<IncomingOrderEvent> inputDisruptor, DisruptorProducer<IncomingOrderEvent, byte[]> producer, WebSocketServer webSocketServer, IoAcceptor ioAcceptor)
	{
		this.inputDisruptor = inputDisruptor;
		this.producer = producer;
		this.webSocketServer = webSocketServer;
		this.ioAcceptor = ioAcceptor;
	}
	
    public void onData(byte[] bb)
    {
    	producer.onData(bb);
    }
}
