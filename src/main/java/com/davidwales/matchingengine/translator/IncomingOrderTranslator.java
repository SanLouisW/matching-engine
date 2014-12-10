package com.davidwales.matchingengine.translator;

import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.input.event.IncomingOrderEvent;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.EventTranslatorTwoArg;

public class IncomingOrderTranslator implements EventTranslatorTwoArg<IncomingOrderEvent, byte[], IoSession> 
{
    
	public void translateTo(IncomingOrderEvent event, long sequence, byte[] bb, IoSession session)
    {
        event.setRawData(bb);
        event.setSession(session);
    }

	
}
