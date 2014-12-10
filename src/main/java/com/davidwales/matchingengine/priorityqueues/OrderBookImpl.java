package com.davidwales.matchingengine.priorityqueues;

import java.util.PriorityQueue;

import com.davidwales.matchingengine.messagecomposition.MessageComposition;
import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.output.disruptor.ExecutedOrder;
import com.davidwales.matchingengine.output.disruptor.ExecutedOrderFactory;
import com.google.inject.Inject;

public class OrderBookImpl implements OrderBook<MessageComposition>
{
	
	private PriorityQueue<MessageComposition> buyQueue;
	
	private PriorityQueue<MessageComposition> sellQueue;
	
	
	@Inject
	public OrderBookImpl(PriorityQueue<MessageComposition> sellQueue, PriorityQueue<MessageComposition> buyQueue, ExecutedOrderOutput executedOrderOutput, ExecutedOrderFactory executedOrderFactory)
	{
		this.buyQueue = buyQueue; 
		this.sellQueue = sellQueue; 
	}


	@Override
	public MessageComposition buyQueuePeek() 
	{
		return this.buyQueue.peek();
	}


	@Override
	public MessageComposition sellQueuePeek() 
	{
		return this.sellQueue.peek();
	}


	@Override
	public void remove(MessageComposition messageComposition) 
	{
		if(messageComposition.getBuy())
		{
			buyQueue.remove(messageComposition);
		}
		else
		{
			sellQueue.remove(messageComposition);
		}
	}


	@Override
	public void put(MessageComposition messageComposition) {
		if(messageComposition.getBuy())
		{
			buyQueue.add(messageComposition);
		}
		else
		{
			sellQueue.add(messageComposition);
		}
		
	}
}
