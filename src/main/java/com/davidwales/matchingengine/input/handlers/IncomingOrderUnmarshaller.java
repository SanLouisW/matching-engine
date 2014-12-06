package com.davidwales.matchingengine.input.handlers;

import com.davidwales.matchingengine.input.event.IncomingOrderEvent;
import com.davidwales.matchingengine.parser.Parser;
import com.google.inject.Inject;
import com.lmax.disruptor.EventHandler;

public class IncomingOrderUnmarshaller implements EventHandler<IncomingOrderEvent> 
{
	@Inject
	private Parser parser;
	
	public IncomingOrderUnmarshaller(Parser parser)
	{
		this.parser = parser;
	}
	
	public void onEvent(IncomingOrderEvent arg0, long arg1, boolean arg2) throws Exception 
	{
		parser.parseData(arg0.getRawData(), arg0.getUnmarshalledMessage());
	}

}


