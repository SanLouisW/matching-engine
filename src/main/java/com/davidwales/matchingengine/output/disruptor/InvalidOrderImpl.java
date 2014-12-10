package com.davidwales.matchingengine.output.disruptor;

import org.apache.mina.core.session.IoSession;

public class InvalidOrderImpl extends ExecutedOrderImpl
{

	public InvalidOrderImpl(boolean isValid, IoSession session)
	{
		this.isValid = isValid;
		this.session = session;
	}
	
	@Override
	public void respond()
	{
		this.session.write("Invalid Fix Message");
	}
}
