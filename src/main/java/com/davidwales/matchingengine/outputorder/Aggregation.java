package com.davidwales.matchingengine.outputorder;

import com.davidwales.matchingengine.inputorder.OrderStatus;

public interface Aggregation 
{
	
	public void incrementDecrementAggregation(OrderStatus oldStatus, OrderStatus newStatus);

	public int getFills();
	
	public int getNews();
	
	public int getPartials();
	
}
