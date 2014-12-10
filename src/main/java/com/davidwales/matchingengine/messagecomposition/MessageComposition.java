package com.davidwales.matchingengine.messagecomposition;

import com.davidwales.matchingengine.priorityqueues.OrderStatus;

public interface MessageComposition 
{
	public int getPrice();
	
	public boolean getBuy();
	
	public int getQuantity();
	
	public void place();
	
	public void updateFilled(int amount);

	OrderStatus getOldStatus();

	OrderStatus getNewStatus();
}
