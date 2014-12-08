package com.davidwales.matchingengine.input.di;

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
/*	AggregatorServer s = new AggregatorServer( 8887 );
	s.start();	*/
	
	@Inject
	public DisruptorComposition(Disruptor<IncomingOrderEvent> inputDisruptor, DisruptorProducer<IncomingOrderEvent, byte[]> producer, WebSocketServer webSocketServer)
	{
		this.inputDisruptor = inputDisruptor;
		this.producer = producer;
		this.webSocketServer = webSocketServer;
	}
	
    public void onData(byte[] bb)
    {
    	producer.onData(bb);
    }
}
