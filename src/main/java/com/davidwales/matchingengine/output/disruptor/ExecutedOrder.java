package com.davidwales.matchingengine.output.disruptor;

import com.davidwales.matchingengine.priorityqueues.OrderStatus;

public interface ExecutedOrder 
{

	public OrderStatus getOldStatus();
	
	public OrderStatus getNewStatus();
	
	public String getSymbol();
	
	public int getQuantity();
	
	public int getPrice();
	
	public boolean getBuy();

}
