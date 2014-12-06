package com.davidwales.matchingengine.input.handlers;

import com.davidwales.matchingengine.input.event.IncomingOrderEvent;
import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.parser.Parser;
import com.google.inject.Inject;
import com.lmax.disruptor.EventHandler;

public class IncomingOrderUnmarshaller implements EventHandler<IncomingOrderEvent> 
{
	private Parser<TagValueMessage> parser;
	
	@Inject
	public IncomingOrderUnmarshaller(Parser<TagValueMessage> parser)
	{
		this.parser = parser;
	}
	
	public void onEvent(IncomingOrderEvent arg0, long arg1, boolean arg2) throws Exception 
	{
		parser.parseData(arg0.getRawData(), arg0.getUnmarshalledMessage());
	}

}


