package com.davidwales.matchingengine.translator;

import com.davidwales.matchingengine.input.event.IncomingOrderEvent;
import com.lmax.disruptor.EventTranslatorOneArg;

public class IncomingOrderTranslator implements EventTranslatorOneArg<IncomingOrderEvent, byte[]> {
    
	public void translateTo(IncomingOrderEvent event, long sequence, byte[] bb)
    {
        event.setRawData(bb);
    }
	
}
