package com.davidwales.matchingengine.priorityqueues;

import com.davidwales.matchingengine.messages.TagValueMessage;

public interface OrderBook<T extends TagValueMessage> {

	public void put(T message);
	
	public void attemptMatch();
}
