package com.davidwales.matchingengine.output.disruptor.handlers;

import com.davidwales.matchingengine.output.disruptor.ExecutedOrder;
import com.davidwales.matchingengine.output.disruptor.OrderOutputEvent;
import com.davidwales.matchingengine.responder.MessageResponder;
import com.google.inject.Inject;
import com.lmax.disruptor.EventHandler;

public class OrderOutputResponder implements EventHandler<OrderOutputEvent>  
{
	private MessageResponder messageResponder;
	
	@Inject
	public OrderOutputResponder(MessageResponder messageResponder)
	{
		this.messageResponder = messageResponder;
	}
	
	@Override
	public void onEvent(OrderOutputEvent event, long sequence, boolean endOfBatch) throws Exception 
	{
		ExecutedOrder order = event.getExecutedOrder();
		messageResponder.respond(order);
	}

}
