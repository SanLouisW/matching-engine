package com.davidwales.matchingengine.mina;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.input.event.DisruptorProducer;
import com.davidwales.matchingengine.input.event.IncomingOrderEvent;
import com.google.inject.Inject;

public class NetworkInputHandler extends IoHandlerAdapter 
{
	private DisruptorProducer<IncomingOrderEvent, byte[]> disruptorProducer;
	
	private ConcurrentHashMap<String, IoSession> clientIdToSessionMap;
	
	@Inject
	public NetworkInputHandler(DisruptorProducer<IncomingOrderEvent, byte[]> disruptorProducer, ConcurrentHashMap<String, IoSession> clientIdToSessionMap)
	{
		this.disruptorProducer = disruptorProducer;
		this.clientIdToSessionMap = clientIdToSessionMap;
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
    	// Inefficient, but i'm rolling with it as POC
    	// we really want to be doing this in the main disruptor
    	// will fix later
    	// Violate Single responsibility principle
    	String messageString = (String) message;
    	
    	if(checkIfClientIdentificationMessage(session,  messageString))
    	{
    		disruptorProducer.onData(messageString.getBytes());
    	}
    }
    
    @Override
    public void sessionIdle( IoSession session, IdleStatus status ) throws Exception
    {
        System.out.println( "IDLE " + session.getIdleCount( status ));
    }
    
    public boolean checkIfClientIdentificationMessage(IoSession session, String message)
    {
    	//Assumes message is always a certain length, again rolling with as poc
    	
    	String clientIdIdentifier = message.substring(0, 4);
    	
    	if(clientIdIdentifier.equals("CLI="))
    	{
    		clientIdToSessionMap.put(message.substring(4), session);
    		return true;
    	}
    	return false;
		
    }
}
