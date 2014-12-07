package com.davidwales.matchingengine.output.disruptor.handlers;

import com.davidwales.matchingengine.output.disruptor.OrderOutputEvent;
import com.lmax.disruptor.EventHandler;

public class OutputOrderPersister implements EventHandler<OrderOutputEvent> 
{

	@Override
	public void onEvent(OrderOutputEvent event, long sequence, boolean endOfBatch) throws Exception 
	{
		System.out.println("order Persister thread");
		
	}

}
