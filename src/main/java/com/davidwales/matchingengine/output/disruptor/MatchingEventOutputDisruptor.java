package com.davidwales.matchingengine.output.disruptor;

import com.davidwales.matchingengine.input.event.DisruptorProducer;
import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.priorityqueues.ExecutedOrderOutput;
import com.davidwales.matchingengine.priorityqueues.OrderStatus;
import com.google.inject.Inject;
import com.lmax.disruptor.dsl.Disruptor;

public class MatchingEventOutputDisruptor implements ExecutedOrderOutput
{
	
	
	private final Disruptor<OrderOutputEvent> outputDisruptor;
	
	private final DisruptorProducer<OrderOutputEvent, ExecutedOrder> producer;
	
	@Inject
	public MatchingEventOutputDisruptor(Disruptor<OrderOutputEvent> outputDisruptor, DisruptorProducer<OrderOutputEvent, ExecutedOrder> producer)
	{
		this.outputDisruptor = outputDisruptor;
		this.producer = producer;
		
	}
	
	@Override
	public void put(ExecutedOrder executedOrder)
	{
		//TODO Haaaaaaaaaack
		producer.onData(executedOrder, null);
	}
	
}
