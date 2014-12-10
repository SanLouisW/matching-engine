package com.davidwales.matchingengine.output.disruptor.handlers;

import com.davidwales.matchingengine.output.disruptor.OrderOutputEvent;
import com.lmax.disruptor.EventHandler;

public class OrderOutputResponder implements EventHandler<OrderOutputEvent>  
{
	
	@Override
	public void onEvent(OrderOutputEvent event, long sequence, boolean endOfBatch) throws Exception 
	{
		event.getExecutedOrder().respond();
	}

}
