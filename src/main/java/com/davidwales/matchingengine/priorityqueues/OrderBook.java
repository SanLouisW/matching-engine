package com.davidwales.matchingengine.priorityqueues;

import com.davidwales.matchingengine.messages.TagValueMessage;

public interface OrderBook<T extends TagValueMessage> {

	void put(T message);
	
	void attemptMatch();
}
