package com.davidwales.matchingengine.inputorder;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.session.IoSession;

import com.carrotsearch.hppc.ObjectObjectOpenHashMap;
import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.outputorder.Aggregation;
import com.davidwales.matchingengine.outputorder.ExecutedOrderOutput;
import com.davidwales.matchingengine.outputorder.OutputOrderFactory;
import com.google.inject.Inject;

public class InputOrderFactoryImpl implements InputOrderFactory 
{
	
	private final ExecutedOrderOutput executedOrderOutput;
	
	private final OutputOrderFactory executedOrderFactory;
	
	private ObjectObjectOpenHashMap<String, OrderBook<InputOrder>> symbolToOrderBook;
	
	private  ConcurrentHashMap<String, Aggregation> symbolToAggregation;
	
	@Inject
	public InputOrderFactoryImpl( ObjectObjectOpenHashMap<String, OrderBook<InputOrder>> symbolToOrderBook,  ConcurrentHashMap<String, Aggregation> symbolToAggregation, ExecutedOrderOutput executedOrderOutput, 	OutputOrderFactory executedOrderFactory)
	{
		this.symbolToOrderBook = symbolToOrderBook;
		this.symbolToAggregation = symbolToAggregation;
		this.executedOrderFactory = executedOrderFactory;
		this.executedOrderOutput = executedOrderOutput;
	}

	@Override
	public InputOrder newInstance(TagValueMessage tagValueMessage, IoSession session) 
	{
		
		OrderBook<InputOrder> orderBook = null;
		Aggregation aggregation = null;
		
		if(tagValueMessage.getIsValid())
		{
			orderBook = symbolToOrderBook.get(new String(tagValueMessage.getSymbol()));
			aggregation = symbolToAggregation.get(new String(tagValueMessage.getSymbol()));
		}
		
		if(orderBook != null && aggregation != null && tagValueMessage.getIsValid())
		{
			return new InputOrderImpl(orderBook,session,tagValueMessage.getQuantity(), tagValueMessage.getPrice(),new String(tagValueMessage.getClientId()), new String(tagValueMessage.getSymbol()), tagValueMessage.getBuy(), executedOrderOutput, executedOrderFactory);
		}
		else
		{
			return new InvalidInputOrderImpl(orderBook, session,tagValueMessage.getQuantity(), tagValueMessage.getPrice(),null, null, tagValueMessage.getBuy(), executedOrderOutput, executedOrderFactory);
		}
		
	}

}
