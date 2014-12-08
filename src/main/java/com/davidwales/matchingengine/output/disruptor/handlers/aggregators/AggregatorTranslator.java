package com.davidwales.matchingengine.output.disruptor.handlers.aggregators;

import com.davidwales.matchingengine.output.disruptor.ExecutedOrder;

public interface AggregatorTranslator 
{
	public void aggregateOrder(ExecutedOrder executedOrder);
}
