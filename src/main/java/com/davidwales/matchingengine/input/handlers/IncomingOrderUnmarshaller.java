package com.davidwales.matchingengine.input.handlers;

import com.davidwales.matchingengine.inputorder.InputEvent;
import com.davidwales.matchingengine.messages.Parser;
import com.davidwales.matchingengine.messages.TagValueMessage;
import com.google.inject.Inject;
import com.lmax.disruptor.EventHandler;

public class IncomingOrderUnmarshaller implements EventHandler<InputEvent> 
{
	private Parser<TagValueMessage> parser;
	
	@Inject
	public IncomingOrderUnmarshaller(Parser<TagValueMessage> parser)
	{
		this.parser = parser;
	}
	
	public void onEvent(InputEvent arg0, long arg1, boolean arg2) throws Exception 
	{
		TagValueMessage message = arg0.getUnmarshalledMessage();
		byte[] rawData = arg0.getRawData();
		
		parser.parseData(rawData, message);
	}

}


