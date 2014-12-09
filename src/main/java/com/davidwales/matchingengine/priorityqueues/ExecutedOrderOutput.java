package com.davidwales.matchingengine.priorityqueues;

import com.davidwales.matchingengine.messages.TagValueMessage;

public interface ExecutedOrderOutput 
{
	void put(TagValueMessage message, OrderStatus oldStatus, OrderStatus newStatus, int quantity, int price);
}
