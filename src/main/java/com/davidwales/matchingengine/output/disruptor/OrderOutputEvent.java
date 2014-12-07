package com.davidwales.matchingengine.output.disruptor;

public class OrderOutputEvent 
{
	
	private ExecutedOrder executedOrder;
	
	public OrderOutputEvent(ExecutedOrder executedOrder)
	{
		this.executedOrder = executedOrder;
	}

	public ExecutedOrder getExecutedOrder() 
	{
		return executedOrder;
	}

	public void setExecutedOrder(ExecutedOrder executedOrder) 
	{
		this.executedOrder = executedOrder;
	}

}
