package com.davidwales.matchingengine.priorityqueues;

import com.davidwales.matchingengine.messages.TagValueMessage;

public interface ExecutedOrderOutput 
{
	void put(TagValueMessage message);
	
	void put(TagValueMessage buyMessage, TagValueMessage sellMessage);
}
