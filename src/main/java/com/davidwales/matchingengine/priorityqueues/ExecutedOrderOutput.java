package com.davidwales.matchingengine.priorityqueues;

import com.davidwales.matchingengine.output.disruptor.ExecutedOrder;

public interface ExecutedOrderOutput 
{
	public void put(ExecutedOrder executedOrder);
}
