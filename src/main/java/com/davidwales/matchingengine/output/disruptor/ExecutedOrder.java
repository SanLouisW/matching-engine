package com.davidwales.matchingengine.output.disruptor;

import com.davidwales.matchingengine.priorityqueues.OrderStatus;

public interface ExecutedOrder 
{

	OrderStatus getOldStatus();
	
	OrderStatus getNewStatus();
	
	String getSymbol();
	
	int getQuantity();
	
	int getPrice();
	
	boolean getBuy();

}
