package com.davidwales.matchingengine.outputorder;

import org.apache.mina.core.session.IoSession;

public class InvalidOutputOrderImpl extends OutputOrderImpl
{

	public InvalidOutputOrderImpl(boolean isValid, IoSession session)
	{
		this.isValid = isValid;
		this.session = session;
	}
	
	@Override
	public void respond()
	{
		System.out.println("Invalid order - will respond to client");
		this.session.write("Invalid Fix Message");
	}
	
	@Override
	public void aggregate() 
	{
		System.out.println("Invalid order - System will not aggregate");
	}

}
