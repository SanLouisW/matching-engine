package com.davidwales.matchingengine.network;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.inputorder.InputEvent;
import com.google.inject.Inject;
import com.lmax.disruptor.EventTranslatorTwoArg;
import com.lmax.disruptor.RingBuffer;

public class NetworkInputHandler extends IoHandlerAdapter 
{

	private final RingBuffer<InputEvent> ringBuffer;
	private final EventTranslatorTwoArg<InputEvent, byte[], IoSession> translator;
	
	@Inject
	public NetworkInputHandler(EventTranslatorTwoArg<InputEvent, byte[], IoSession> translator, RingBuffer<InputEvent> ringBuffer)
	{
		this.translator = translator;
		this.ringBuffer = ringBuffer; 
	}
	
    @Override
    public void exceptionCaught( IoSession session, Throwable cause ) throws Exception
    {
        cause.printStackTrace();
    }
    
	@Override
    public void messageReceived( IoSession session, Object message ) throws Exception
    {
    	String messageString = (String) message;
    	ringBuffer.publishEvent(translator, messageString.getBytes(), (IoSession)session);
    }
    
    @Override
    public void sessionIdle( IoSession session, IdleStatus status ) throws Exception
    {
        System.out.println( "IDLE " + session.getIdleCount( status ));
    }
    
}
