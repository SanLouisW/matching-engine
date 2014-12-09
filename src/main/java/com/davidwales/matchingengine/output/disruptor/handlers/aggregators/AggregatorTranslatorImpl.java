package com.davidwales.matchingengine.output.disruptor.handlers.aggregators;

import java.util.concurrent.ConcurrentHashMap;

import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.output.disruptor.ExecutedOrder;
import com.davidwales.matchingengine.output.disruptor.handlers.Aggregation;
import com.davidwales.matchingengine.output.disruptor.handlers.OrderAggregation;
import com.google.inject.Inject;

public class AggregatorTranslatorImpl implements AggregatorTranslator
{
	ConcurrentHashMap<String, Aggregation> symbolToAggregation;
	
	@Inject
	public AggregatorTranslatorImpl(ConcurrentHashMap<String, Aggregation> symbolToAggregation)
	{
		this.symbolToAggregation = symbolToAggregation;
	}
	
	@Override
	public void aggregateOrder(ExecutedOrder executedOrder)
	{
		symbolToAggregation.get(executedOrder.getSymbol()).incrementDecrementAggregation(executedOrder);
	}
}
