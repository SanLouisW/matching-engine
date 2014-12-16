package com.davidwales.matchingengine.inputorder;


public interface InputOrder 
{
	public int getPrice();
	
	public boolean getBuy();
	
	public int getQuantity();
	
	public void place();
	
	public void updateFilled(int amount);

	OrderStatus getOldStatus();

	OrderStatus getNewStatus();
}
