package com.davidwales.matchingengine.priorityqueues;

import com.carrotsearch.hppc.ObjectObjectOpenHashMap;
import com.davidwales.matchingengine.messages.TagValueMessage;
import com.google.inject.Inject;

public class InstrumentMatcherImpl implements InstrumentsMatcher<TagValueMessage>
{

	ObjectObjectOpenHashMap<String, OrderBook<TagValueMessage>> symbolToOrderBook;
	
	@Inject
	public InstrumentMatcherImpl(ObjectObjectOpenHashMap<String, OrderBook<TagValueMessage>> symbolToOrderBook)
	{
		this.symbolToOrderBook = symbolToOrderBook;
	}
	
	@Override
	public void put(TagValueMessage tagValueMessage) 
	{
		String symbol = new String(tagValueMessage.getSymbol());
		
		OrderBook<TagValueMessage> orderBook = symbolToOrderBook.get(symbol);
		
		orderBook.put(tagValueMessage);
		
		orderBook.attemptMatch();
		
	}

}
