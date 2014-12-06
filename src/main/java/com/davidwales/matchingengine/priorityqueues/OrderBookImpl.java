package com.davidwales.matchingengine.priorityqueues;

import java.util.PriorityQueue;

import com.davidwales.matchingengine.messages.TagValueMessage;

public class OrderBookImpl implements OrderBook<TagValueMessage>
{
	PriorityQueue<TagValueMessage> buyQueue;
	
	PriorityQueue<TagValueMessage> sellQueue;
	
	ExecutedOrderOutput executedOrderOutput;
	
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
		TagValueMessage buy = buyQueue.poll();
		TagValueMessage sell = sellQueue.poll();
		
		if(buy.getPrice() <= sell.getPrice()){
			executedOrderOutput.put(buy, sell);
			buyQueue.remove(buy);
			buyQueue.remove(sell);
			attemptMatch();
		}
	}
	
}
