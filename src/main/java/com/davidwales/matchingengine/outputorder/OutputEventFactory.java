package com.davidwales.matchingengine.outputorder;

import com.google.inject.Inject;
import com.lmax.disruptor.EventFactory;

public class OutputEventFactory implements EventFactory<OutputEvent>
{
	
	private OutputOrderFactory executedOrderFactory;

	@Inject
	public OutputEventFactory(OutputOrderFactory executedOrderFactory)
	{
		this.executedOrderFactory = executedOrderFactory;
	}
	
	@Override
	public OutputEvent newInstance() 
	{
		return new OutputEvent(executedOrderFactory.newInstance());
		
	}

}
