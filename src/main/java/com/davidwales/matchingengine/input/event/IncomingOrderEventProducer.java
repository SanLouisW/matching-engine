package com.davidwales.matchingengine.input.event;

import org.apache.mina.core.session.IoSession;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lmax.disruptor.EventTranslatorTwoArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

//Use Generics To get rid of output producer
@Singleton
public class IncomingOrderEventProducer implements DisruptorProducer<IncomingOrderEvent, byte[]> {
	
    private final RingBuffer<IncomingOrderEvent> ringBuffer;

    private final EventTranslatorTwoArg<IncomingOrderEvent, byte[], IoSession> translator;
    
    @Inject
    public IncomingOrderEventProducer(Disruptor<IncomingOrderEvent> disruptor, EventTranslatorTwoArg<IncomingOrderEvent, byte[], IoSession> translator)
    {
    	//TODO look into Guice to do this, currently hard to test
        this.ringBuffer = disruptor.getRingBuffer();
        this.translator = translator;
    }

    //TODO neaten this up, hack for now
    public void onData(byte[] bb, Object session)
    {   
        ringBuffer.publishEvent(translator, bb, (IoSession)session);
    }

	@Override
	public void run() {
 
		
	}


}
