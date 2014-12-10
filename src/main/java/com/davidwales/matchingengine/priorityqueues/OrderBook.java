package com.davidwales.matchingengine.priorityqueues;

import com.davidwales.matchingengine.messagecomposition.MessageComposition;

public interface OrderBook<T extends MessageComposition> 
{

	public MessageComposition buyQueuePeek();
	
	public MessageComposition sellQueuePeek();
	
	public void remove(MessageComposition messageComposition);
	
	public void put(MessageComposition messageComposition);
}
