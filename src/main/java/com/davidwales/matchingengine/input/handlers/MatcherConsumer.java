package com.davidwales.matchingengine.input.handlers;

import com.davidwales.matchingengine.input.event.IncomingOrderEvent;
import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.priorityqueues.InstrumentsMatcher;
import com.google.inject.Inject;
import com.lmax.disruptor.EventHandler;


public class MatcherConsumer implements EventHandler<IncomingOrderEvent> 
{

	InstrumentsMatcher<TagValueMessage> instrumentsMatcher;
	
	@Inject
	public MatcherConsumer(InstrumentsMatcher<TagValueMessage> instrumentsMatcher)
	{
		this.instrumentsMatcher = instrumentsMatcher;
	}
	
	public void onEvent(IncomingOrderEvent arg0, long arg1, boolean arg2) throws Exception 
	{
		instrumentsMatcher.put(arg0.getUnmarshalledMessage());
	}

}