package com.davidwales.matchingengine.inputorder;


public interface OrderBook<T extends InputOrder> 
{

	public InputOrder buyQueuePeek();
	
	public InputOrder sellQueuePeek();
	
	public void remove(InputOrder messageComposition);
	
	public void put(InputOrder messageComposition);
}
