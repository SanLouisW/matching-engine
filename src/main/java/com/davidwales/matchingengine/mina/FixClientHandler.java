package com.davidwales.matchingengine.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class FixClientHandler extends IoHandlerAdapter 
{
	private final String values;
	
	private boolean finished;
	 
	public FixClientHandler(String values)
	{
		this.values = values;
	}
	
	public boolean isFinished()
	{
		return finished;
	}
		 
	@Override
	public void sessionOpened(IoSession session)
	{
		session.write(values);
	}
		 
	@Override
	public void messageReceived(IoSession session, Object message)
	{
		System.out.println(message);
	}
		 
		 @Override
	public void exceptionCaught(IoSession session, Throwable cause)
	{
		session.close();
	}
	 
}
