package com.davidwales.matchingengine.input.di;

import com.davidwales.matchingengine.input.event.DisruptorProducer;
import com.davidwales.matchingengine.input.event.IncomingOrderEvent;
import com.google.inject.Inject;
import com.lmax.disruptor.dsl.Disruptor;

public class DisruptorComposition {
	
	private final Disruptor<IncomingOrderEvent> inputDisruptor;
	private final DisruptorProducer<IncomingOrderEvent, byte[]> producer;
	
	@Inject
	public DisruptorComposition(Disruptor<IncomingOrderEvent> inputDisruptor, DisruptorProducer<IncomingOrderEvent, byte[]> producer){
		this.inputDisruptor = inputDisruptor;
		this.producer = producer;
	}
	
    public void onData(byte[] bb)
    {
    	producer.onData(bb);
    }
}
