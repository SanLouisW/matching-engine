package com.davidwales.matchingengine.input.event;

import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.messages.TagValueMessage;

public class IncomingOrderEvent 
{

	private IoSession session;
	
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

	public IoSession getSession() {
		return session;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}
	
}
