package com.davidwales.matchingengine.inputorder;

import java.util.PriorityQueue;

import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.outputorder.ExecutedOrderOutput;
import com.davidwales.matchingengine.outputorder.OutputOrder;
import com.davidwales.matchingengine.outputorder.OutputOrderFactory;
import com.google.inject.Inject;

public class OrderBookImpl implements OrderBook<InputOrder>
{
	
	private PriorityQueue<InputOrder> buyQueue;
	
	private PriorityQueue<InputOrder> sellQueue;
	
	
	@Inject
	public OrderBookImpl(PriorityQueue<InputOrder> sellQueue, PriorityQueue<InputOrder> buyQueue, ExecutedOrderOutput executedOrderOutput, OutputOrderFactory executedOrderFactory)
	{
		this.buyQueue = buyQueue; 
		this.sellQueue = sellQueue; 
	}


	@Override
	public InputOrder buyQueuePeek() 
	{
		return this.buyQueue.peek();
	}


	@Override
	public InputOrder sellQueuePeek() 
	{
		return this.sellQueue.peek();
	}


	@Override
	public void remove(InputOrder messageComposition) 
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
	public void put(InputOrder messageComposition) {
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
