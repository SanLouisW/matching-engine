package com.davidwales.matchingengine.input.event;

import javax.security.auth.login.Configuration;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

@Singleton
public class IncomingOrderEventProducer implements DisruptorProducer<IncomingOrderEvent, byte[]> {
	
    private final RingBuffer<IncomingOrderEvent> ringBuffer;

    private final EventTranslatorOneArg<IncomingOrderEvent, byte[]> translator;
    
    @Inject
    public IncomingOrderEventProducer(Disruptor<IncomingOrderEvent> disruptor, EventTranslatorOneArg<IncomingOrderEvent, byte[]> translator)
    {
    	//TODO look into Guice to do this, currently hard to test
        this.ringBuffer = disruptor.getRingBuffer();
        this.translator = translator;
    }

    public void onData(byte[] bb)
    {   
        ringBuffer.publishEvent(translator, bb);
    }

	@Override
	public void run() {
 
		
	}


}
