package com.davidwales.matchingengine.output.disruptor;

import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.priorityqueues.OrderStatus;

public class ExecuteOrderFactoryImpl implements ExecutedOrderFactory 
{
	
	@Override
	public ExecutedOrder newInstance(TagValueMessage message, OrderStatus oldStatus, OrderStatus newStatus, int quantity, int price)
	{
		return new ExecutedOrderImpl(oldStatus, newStatus, new String(message.getSymbol()), quantity, price , message.getBuy());
	}

	@Override
	public ExecutedOrder newInstance() 
	{
		return new ExecutedOrderImpl();
	}
}
