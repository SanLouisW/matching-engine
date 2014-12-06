package com.davidwales.matchingengine.parser;

import com.davidwales.matchingengine.messages.TagValueMessage;

public interface Parser<T extends TagValueMessage>
{
	
	public void parseData(byte[] rawData, T parsedMessage) throws Exception;

}
