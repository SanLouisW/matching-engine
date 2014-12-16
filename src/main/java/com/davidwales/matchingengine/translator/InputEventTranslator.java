package com.davidwales.matchingengine.translator;

import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.inputorder.InputEvent;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.EventTranslatorTwoArg;

public class InputEventTranslator implements EventTranslatorTwoArg<InputEvent, byte[], IoSession> 
{
    
	public void translateTo(InputEvent event, long sequence, byte[] bb, IoSession session)
    {
        event.setRawData(bb);
        event.setSession(session);
    }
	
}
