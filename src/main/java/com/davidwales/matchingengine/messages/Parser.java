package com.davidwales.matchingengine.messages;



public interface Parser<T>
{
	
	public void parseData(byte[] rawData, T parsedMessage) ;
	
}
