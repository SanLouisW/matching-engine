package com.davidwales.matchingengine.output.disruptor;

import com.davidwales.matchingengine.input.event.DisruptorProducer;
import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.priorityqueues.ExecutedOrderOutput;
import com.google.inject.Inject;
import com.lmax.disruptor.dsl.Disruptor;

public class MatchingEventOutputDisruptor implements ExecutedOrderOutput
{
	
	private final Disruptor<OrderOutputEvent> outputDisruptor;
	
	private final DisruptorProducer<OrderOutputEvent, ExecutedOrder> producer;
	
	@Inject
	MatchingEventOutputDisruptor(Disruptor<OrderOutputEvent> outputDisruptor, DisruptorProducer<OrderOutputEvent, ExecutedOrder> producer)
	{
		this.outputDisruptor = outputDisruptor;
		this.producer = producer;
		
	}
	
	@Override
	public void put(TagValueMessage message)
	{
		//TODO stop instantiating a new object every message, bad for GC, Temporary
		producer.onData(new ExecutedOrderImpl(message, null));
	}
	
	@Override
	public void put(TagValueMessage buyMessage, TagValueMessage sellMessage)
	{
		//TODO stop instantiating a new object every message, bad for GC, Temporary
		producer.onData(new ExecutedOrderImpl(buyMessage, sellMessage));
	}
	
}
