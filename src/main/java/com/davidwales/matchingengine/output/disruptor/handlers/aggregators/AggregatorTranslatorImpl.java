package com.davidwales.matchingengine.output.disruptor.handlers.aggregators;

import java.util.concurrent.ConcurrentHashMap;

import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.output.disruptor.ExecutedOrder;
import com.davidwales.matchingengine.output.disruptor.handlers.OrderAggregation;
import com.google.inject.Inject;

public class AggregatorTranslatorImpl implements AggregatorTranslator
{
	ConcurrentHashMap<String, OrderAggregation> symbolToAggregation;
	
	@Inject
	public AggregatorTranslatorImpl(ConcurrentHashMap<String, OrderAggregation> symbolToAggregation)
	{
		this.symbolToAggregation = symbolToAggregation;
	}
	
	@Override
	public void aggregateOrder(ExecutedOrder executedOrder)
	{
		//TODO make this work properly
		TagValueMessage primaryOrder = executedOrder.getPrimaryOrder();
		TagValueMessage secondaryOrder = executedOrder.getSecondaryOrder();
		symbolToAggregation.get("aaa").incrementNews();
		
	}
}
