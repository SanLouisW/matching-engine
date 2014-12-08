package com.davidwales.matchingengine.output.disruptor.handlers;


import com.davidwales.matchingengine.output.disruptor.OrderOutputEvent;
import com.lmax.disruptor.EventHandler;

public class OutputOrderAggregator implements EventHandler<OrderOutputEvent>  
{
	
	public OutputOrderAggregator() throws Exception
	{
        
	}
	
	@Override
	public void onEvent(OrderOutputEvent event, long sequence, boolean endOfBatch) throws Exception 
	{
		
	}

}
