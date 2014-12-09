package com.davidwales.matchingengine.output.disruptor.handlers;

import com.davidwales.matchingengine.output.disruptor.ExecutedOrder;

public interface Aggregation 
{
	
	public void incrementDecrementAggregation(ExecutedOrder executedOrder);
	
}
