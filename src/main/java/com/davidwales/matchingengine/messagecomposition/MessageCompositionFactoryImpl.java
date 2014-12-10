package com.davidwales.matchingengine.messagecomposition;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.session.IoSession;

import com.carrotsearch.hppc.ObjectObjectOpenHashMap;
import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.output.disruptor.ExecutedOrderFactory;
import com.davidwales.matchingengine.output.disruptor.handlers.Aggregation;
import com.davidwales.matchingengine.priorityqueues.ExecutedOrderOutput;
import com.davidwales.matchingengine.priorityqueues.OrderBook;
import com.google.inject.Inject;

public class MessageCompositionFactoryImpl implements MessageCompositionFactory 
{
	
	private final ExecutedOrderOutput executedOrderOutput;
	private final ExecutedOrderFactory executedOrderFactory;
	private ObjectObjectOpenHashMap<String, OrderBook<MessageComposition>> symbolToOrderBook;
	
	private  ConcurrentHashMap<String, Aggregation> symbolToAggregation;
	
	@Inject
	public MessageCompositionFactoryImpl( ObjectObjectOpenHashMap<String, OrderBook<MessageComposition>> symbolToOrderBook,  ConcurrentHashMap<String, Aggregation> symbolToAggregation, ExecutedOrderOutput executedOrderOutput, 	ExecutedOrderFactory executedOrderFactory)
	{
		this.symbolToOrderBook = symbolToOrderBook;
		this.symbolToAggregation = symbolToAggregation;
		this.executedOrderFactory = executedOrderFactory;
		this.executedOrderOutput = executedOrderOutput;
	}

	@Override
	public MessageComposition newInstance(TagValueMessage tagValueMessage, IoSession session) 
	{
		
		OrderBook<MessageComposition> orderBook = null;
		Aggregation aggregation = null;
		
		if(tagValueMessage.getIsValid())
		{
			orderBook = symbolToOrderBook.get(new String(tagValueMessage.getSymbol()));
			aggregation = symbolToAggregation.get(new String(tagValueMessage.getSymbol()));
		}
		
		if(orderBook != null && aggregation != null && tagValueMessage.getIsValid())
		{
			return new MessageCompositionImpl(orderBook,session,tagValueMessage.getQuantity(), tagValueMessage.getPrice(),new String(tagValueMessage.getClientId()), new String(tagValueMessage.getSymbol()), tagValueMessage.getBuy(), executedOrderOutput, executedOrderFactory);
		}
		else
		{
			return new InvalidMessageCompositionImpl(orderBook, session,tagValueMessage.getQuantity(), tagValueMessage.getPrice(),null, null, tagValueMessage.getBuy(), executedOrderOutput, executedOrderFactory);
		}
		
	}

}
