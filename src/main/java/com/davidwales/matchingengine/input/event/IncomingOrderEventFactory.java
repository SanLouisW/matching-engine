package com.davidwales.matchingengine.input.event;

import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.messages.TagValueMessageFactory;
import com.lmax.disruptor.EventFactory;

public class IncomingOrderEventFactory implements EventFactory<IncomingOrderEvent> 
{
	
	private final TagValueMessageFactory tagValueMessageFactory;
	
	public IncomingOrderEventFactory(TagValueMessageFactory tagValueMessageFactory)
	{
		this.tagValueMessageFactory = tagValueMessageFactory;
	}
	
	public IncomingOrderEvent newInstance()
	{
		final TagValueMessage tagValueMessage = tagValueMessageFactory.newInstance();
		return new IncomingOrderEvent(tagValueMessage);
	}
}
