package com.davidwales.matchingengine.priorityqueues;

import java.util.PriorityQueue;

import com.davidwales.matchingengine.messages.TagValueMessage;
import com.google.inject.Inject;

public class OrderBookImpl implements OrderBook<TagValueMessage>
{
	PriorityQueue<TagValueMessage> buyQueue;
	
	PriorityQueue<TagValueMessage> sellQueue;
	
	ExecutedOrderOutput executedOrderOutput;
	
	@Inject
	public OrderBookImpl(PriorityQueue<TagValueMessage> sellQueue, PriorityQueue<TagValueMessage> buyQueue, ExecutedOrderOutput executedOrderOutput)
	{
		this.buyQueue = buyQueue; 
		this.sellQueue = sellQueue; 
		this.executedOrderOutput = executedOrderOutput;
	}
	
	@Override
	public void put(TagValueMessage message)
	{
		executedOrderOutput.put(message);
		
		if(message.getBuy())
		{
			buyQueue.add(message);
		}
		else
		{
			sellQueue.add(message);
		}
	}
	
	@Override
	public void attemptMatch()
	{
		TagValueMessage buy = buyQueue.peek();
		TagValueMessage sell = sellQueue.peek();
		
		if(buy != null && sell != null && 
				buy.getPrice() <= sell.getPrice())
		{
			executedOrderOutput.put(buy, sell);
			buyQueue.remove(buy);
			buyQueue.remove(sell);
			attemptMatch();
		}
	}
	
}
