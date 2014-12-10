package com.davidwales.matchingengine.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.input.event.DisruptorProducer;
import com.davidwales.matchingengine.input.event.IncomingOrderEvent;
import com.google.inject.Inject;

public class NetworkInputHandler extends IoHandlerAdapter 
{
	private DisruptorProducer<IncomingOrderEvent, byte[]> disruptorProducer;
	
	
	@Inject
	public NetworkInputHandler(DisruptorProducer<IncomingOrderEvent, byte[]> disruptorProducer)
	{
		this.disruptorProducer = disruptorProducer;
	}
	
    @Override
    public void exceptionCaught( IoSession session, Throwable cause ) throws Exception
    {
        cause.printStackTrace();
    }
    
    @SuppressWarnings("deprecation")
	@Override
    public void messageReceived( IoSession session, Object message ) throws Exception
    {
    	String messageString = (String) message;
    	disruptorProducer.onData(messageString.getBytes(), session);
    }
    
    @Override
    public void sessionIdle( IoSession session, IdleStatus status ) throws Exception
    {
        System.out.println( "IDLE " + session.getIdleCount( status ));
    }
    
}
