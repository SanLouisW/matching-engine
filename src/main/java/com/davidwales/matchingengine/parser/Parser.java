package com.davidwales.matchingengine.parser;

import com.davidwales.matchingengine.messages.TagValueMessage;

public interface Parser 
{
	
	public void parseData(byte[] rawData, TagValueMessage parsedMessage) throws Exception;

}
