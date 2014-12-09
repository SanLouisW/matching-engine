package com.davidwales.matchingengine.output.disruptor.handlers;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.input.di.annotations.Responder;
import com.davidwales.matchingengine.output.disruptor.ExecutedOrder;
import com.davidwales.matchingengine.output.disruptor.OrderOutputEvent;
import com.davidwales.matchingengine.responder.MessageResponder;
import com.google.inject.Inject;
import com.lmax.disruptor.EventHandler;

public class OrderOutputResponder implements EventHandler<OrderOutputEvent>  
{
	private MessageResponder messageResponder;
	
	private ConcurrentHashMap<String, IoSession> clientIdToSessionMap;

	@Inject
	public OrderOutputResponder(ConcurrentHashMap<String, IoSession> clientIdToSessionMap, MessageResponder messageResponder)
	{
		this.messageResponder = messageResponder;
		this.clientIdToSessionMap = clientIdToSessionMap;
	}
	
	@Override
	public void onEvent(OrderOutputEvent event, long sequence, boolean endOfBatch) throws Exception 
	{
		ExecutedOrder order = event.getExecutedOrder();
		IoSession session = clientIdToSessionMap.get(order.getClientId());
		
		messageResponder.respond(order, session);
	}

}
