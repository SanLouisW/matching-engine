package com.davidwales.matchingengine.priorityqueues;

import java.util.PriorityQueue;

import com.davidwales.matchingengine.messages.TagValueMessage;
import com.google.inject.Inject;

public class OrderBookImpl implements OrderBook<TagValueMessage>
{
	
	private PriorityQueue<TagValueMessage> buyQueue;
	
	private PriorityQueue<TagValueMessage> sellQueue;
	
	private ExecutedOrderOutput executedOrderOutput;
	
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
		executedOrderOutput.put(message, OrderStatus.NA, OrderStatus.NEW, message.getQuantity(), message.getPrice());
		
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
		
		if(buy != null && sell != null) 
		{
			int amountToStatisfy = buy.getQuantity() - sell.getQuantity();
			
			if(amountToStatisfy > 0)
			{
				OrderStatus newBuyStatus = buy.isFilled(amountToStatisfy) ? OrderStatus.FILLED : OrderStatus.PARTIAL;
				OrderStatus newSellStatus = sell.isFilled(amountToStatisfy) ? OrderStatus.FILLED : OrderStatus.PARTIAL;
				
				executedOrderOutput.put(buy, buy.getOrderStatus(), newBuyStatus, buy.getQuantity(), buy.getPrice());
				executedOrderOutput.put(sell, sell.getOrderStatus(), newSellStatus, sell.getQuantity(), sell.getPrice());
				
				buy.setOrderStatus(newBuyStatus);
				sell.setOrderStatus(newSellStatus);
				
				if(newBuyStatus == OrderStatus.FILLED)
				{
					buyQueue.remove(buy);
					
				}
				if(newSellStatus == OrderStatus.FILLED)
				{
					sellQueue.remove(sell);
				}
				
				if(newSellStatus == OrderStatus.FILLED || newBuyStatus == OrderStatus.FILLED )
				{
					attemptMatch();
				}
			}
		}
	}
	
}
