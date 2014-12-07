package com.davidwales.matchingengine.output.disruptor;

import com.google.inject.Inject;
import com.lmax.disruptor.EventFactory;

public class OrderOutputEventFactory implements EventFactory<OrderOutputEvent>
{
	
	private ExecutedOrderFactory executedOrderFactory;

	@Inject
	public OrderOutputEventFactory(ExecutedOrderFactory executedOrderFactory)
	{
		this.executedOrderFactory = executedOrderFactory;
	}
	
	@Override
	public OrderOutputEvent newInstance() 
	{
		return new OrderOutputEvent(executedOrderFactory.newInstance());
		
	}

}
