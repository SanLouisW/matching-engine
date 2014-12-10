package com.davidwales.matchingengine.priorityqueues;

import java.util.PriorityQueue;

import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.output.disruptor.ExecutedOrder;
import com.davidwales.matchingengine.output.disruptor.ExecutedOrderFactory;
import com.google.inject.Inject;

public class OrderBookImpl implements OrderBook<TagValueMessage>
{
	
	private final ExecutedOrderFactory executedOrderFactory;
	
	private PriorityQueue<TagValueMessage> buyQueue;
	
	private PriorityQueue<TagValueMessage> sellQueue;
	
	private ExecutedOrderOutput executedOrderOutput;
	
	@Inject
	public OrderBookImpl(PriorityQueue<TagValueMessage> sellQueue, PriorityQueue<TagValueMessage> buyQueue, ExecutedOrderOutput executedOrderOutput, ExecutedOrderFactory executedOrderFactory)
	{
		this.buyQueue = buyQueue; 
		this.sellQueue = sellQueue; 
		this.executedOrderOutput = executedOrderOutput;
		this.executedOrderFactory = executedOrderFactory;
	}
	
	@Override
	public void put(TagValueMessage message)
	{
		ExecutedOrder newOrder = executedOrderFactory.newInstance(message, OrderStatus.NA, OrderStatus.NEW, message.getQuantity(), message.getPrice());
		executedOrderOutput.put(newOrder);
		
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
				
				executedOrderOutput.put(executedOrderFactory.newInstance(buy, buy.getOrderStatus(), newBuyStatus, buy.getQuantity(), buy.getPrice()));
				executedOrderOutput.put(executedOrderFactory.newInstance(sell, sell.getOrderStatus(), newSellStatus, sell.getQuantity(), sell.getPrice()));
				
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
