package com.davidwales.matchingengine.output.disruptor.handlers;

import com.davidwales.matchingengine.outputorder.OutputEvent;
import com.lmax.disruptor.EventHandler;

public class OutputOrderAggregator implements EventHandler<OutputEvent>  
{
	
	@Override
	public void onEvent(OutputEvent event, long sequence, boolean endOfBatch) throws Exception 
	{
		event.getExecutedOrder().aggregate();
	}

}

