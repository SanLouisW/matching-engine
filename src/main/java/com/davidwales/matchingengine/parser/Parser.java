package com.davidwales.matchingengine.parser;


public interface Parser<T>
{
	
	public void parseData(byte[] rawData, T parsedMessage) throws Exception;

}
