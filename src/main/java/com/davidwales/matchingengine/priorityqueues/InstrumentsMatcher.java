package com.davidwales.matchingengine.priorityqueues;

import com.davidwales.matchingengine.messages.TagValueMessage;

public interface InstrumentsMatcher<T extends TagValueMessage>
{
	
	public void put(T tagValueMessage);
	
}
