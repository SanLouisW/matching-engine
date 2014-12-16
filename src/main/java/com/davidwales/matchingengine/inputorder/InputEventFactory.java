package com.davidwales.matchingengine.inputorder;

import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.messages.TagValueMessageFactory;
import com.google.inject.Inject;
import com.lmax.disruptor.EventFactory;

public class InputEventFactory implements EventFactory<InputEvent> 
{

	private final TagValueMessageFactory tagValueMessageFactory;
	
	@Inject
	public InputEventFactory(TagValueMessageFactory tagValueMessageFactory)
	{
		this.tagValueMessageFactory = tagValueMessageFactory;
	}
	
	public InputEvent newInstance()
	{
		final TagValueMessage tagValueMessage = tagValueMessageFactory.newInstance();
		return new InputEvent(tagValueMessage);
	}
}
