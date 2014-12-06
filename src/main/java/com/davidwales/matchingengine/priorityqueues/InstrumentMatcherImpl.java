package com.davidwales.matchingengine.priorityqueues;

import com.carrotsearch.hppc.ObjectObjectOpenHashMap;
import com.davidwales.matchingengine.messages.TagValueMessage;

public class InstrumentMatcherImpl implements InstrumentsMatcher<TagValueMessage>
{

	ObjectObjectOpenHashMap<char[], OrderBook<TagValueMessage>> symbolToOrderBook;
	
	public InstrumentMatcherImpl(ObjectObjectOpenHashMap<char[], OrderBook<TagValueMessage>> symbolToOrderBook)
	{
		this.symbolToOrderBook = symbolToOrderBook;
	}
	
	@Override
	public void put(TagValueMessage tagValueMessage) 
	{
		char[] symbol = tagValueMessage.getSymbol();
		
		OrderBook<TagValueMessage> orderBook = symbolToOrderBook.get(symbol);
		
		orderBook.put(tagValueMessage);
		
		orderBook.attemptMatch();
		
	}

}
