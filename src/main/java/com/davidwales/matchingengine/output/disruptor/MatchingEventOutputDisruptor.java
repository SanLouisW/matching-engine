package com.davidwales.matchingengine.output.disruptor;

import com.davidwales.matchingengine.input.event.DisruptorProducer;
import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.priorityqueues.ExecutedOrderOutput;
import com.davidwales.matchingengine.priorityqueues.OrderStatus;
import com.google.inject.Inject;
import com.lmax.disruptor.dsl.Disruptor;

public class MatchingEventOutputDisruptor implements ExecutedOrderOutput
{
	
	private final ExecutedOrderFactory executedOrderFactory;
	
	private final Disruptor<OrderOutputEvent> outputDisruptor;
	
	private final DisruptorProducer<OrderOutputEvent, ExecutedOrder> producer;
	
	@Inject
	MatchingEventOutputDisruptor(Disruptor<OrderOutputEvent> outputDisruptor, DisruptorProducer<OrderOutputEvent, ExecutedOrder> producer, ExecutedOrderFactory executedOrderFactory)
	{
		this.outputDisruptor = outputDisruptor;
		this.producer = producer;
		this.executedOrderFactory = executedOrderFactory;
		
	}
	
	@Override
	public void put(TagValueMessage message, OrderStatus oldStatus, OrderStatus newStatus, int quantity, int price)
	{
		producer.onData(executedOrderFactory.newInstance(message, oldStatus, newStatus, quantity, price));
	}
	
}
