package com.davidwales.matchingengine.input.event;

import com.davidwales.matchingengine.messages.TagValueMessage;

public class IncomingOrderEvent 
{

	private byte[] rawData;
	
	private final TagValueMessage unmarshalledMessage;

	public IncomingOrderEvent(TagValueMessage unmarshalledMessage)
	{
		rawData = new byte[1000];
		this.unmarshalledMessage = unmarshalledMessage;
	}
	
	public byte[] getRawData() 
	{
		return rawData;
	}

	public void setRawData(byte[] rawData) 
	{
		this.rawData = rawData;
	}

	public TagValueMessage getUnmarshalledMessage() 
	{
		return unmarshalledMessage;
	}
	
}
