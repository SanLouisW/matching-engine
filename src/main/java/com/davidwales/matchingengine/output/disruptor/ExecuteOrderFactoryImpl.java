package com.davidwales.matchingengine.output.disruptor;

import com.davidwales.matchingengine.messages.TagValueMessageFactory;
import com.google.inject.Inject;

public class ExecuteOrderFactoryImpl implements ExecutedOrderFactory 
{
	
	private TagValueMessageFactory tagValueMessageFactory;
	
	@Inject
	public ExecuteOrderFactoryImpl(TagValueMessageFactory tagValueMessageFactory)
	{
		this.tagValueMessageFactory = tagValueMessageFactory;
	}
	
	public ExecutedOrder newInstance()
	{
		
		return new ExecutedOrderImpl(tagValueMessageFactory.newInstance(), tagValueMessageFactory.newInstance());
	}
}
