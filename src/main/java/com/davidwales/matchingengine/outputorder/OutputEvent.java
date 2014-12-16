package com.davidwales.matchingengine.outputorder;

public class OutputEvent 
{
	
	private OutputOrder executedOrder;
	
	public OutputEvent(OutputOrder executedOrder)
	{
		this.executedOrder = executedOrder;
	}

	public OutputOrder getExecutedOrder() 
	{
		return executedOrder;
	}

	public void setExecutedOrder(OutputOrder executedOrder) 
	{
		this.executedOrder = executedOrder;
	}

}
