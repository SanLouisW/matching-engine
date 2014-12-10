package com.davidwales.matchingengine.output.disruptor;

import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.priorityqueues.OrderStatus;

public interface ExecutedOrderFactory 
{
	public ExecutedOrder newInstance(TagValueMessage message, OrderStatus oldStatus, OrderStatus newStatus, int quantity, int price);
	
	public ExecutedOrder newInstance();
	
	public ExecutedOrder invalidOrder(TagValueMessage message);
}
