package com.davidwales.matchingengine.priorityqueues;

import com.carrotsearch.hppc.ObjectObjectOpenHashMap;
import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.output.disruptor.ExecutedOrder;
import com.davidwales.matchingengine.output.disruptor.ExecutedOrderFactory;
import com.google.inject.Inject;

public class InstrumentMatcherImpl implements InstrumentsMatcher<TagValueMessage>
{

	private ObjectObjectOpenHashMap<String, OrderBook<TagValueMessage>> symbolToOrderBook;
	
	private final ExecutedOrderFactory executedOrderFactory;
	
	private ExecutedOrderOutput executedOrderOutput;
	
	@Inject
	public InstrumentMatcherImpl(ObjectObjectOpenHashMap<String, OrderBook<TagValueMessage>> symbolToOrderBook,  ExecutedOrderOutput executedOrderOutput, ExecutedOrderFactory executedOrderFactory)
	{
		this.symbolToOrderBook = symbolToOrderBook;
		this.executedOrderOutput = executedOrderOutput;
		this.executedOrderFactory = executedOrderFactory;
	}
	
	@Override
	public void put(TagValueMessage tagValueMessage) 
	{
		if(tagValueMessage.getIsValid())
		{
			String symbol = new String(tagValueMessage.getSymbol());
			
			OrderBook<TagValueMessage> orderBook = symbolToOrderBook.get(symbol);
			
			orderBook.put(tagValueMessage);
			
			orderBook.attemptMatch();
		}
		else
		{
			ExecutedOrder invalidOrder = executedOrderFactory.invalidOrder(tagValueMessage);
			executedOrderOutput.put(invalidOrder);
		}
	}

}
