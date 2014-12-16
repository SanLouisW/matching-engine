package com.davidwales.matchingengine.outputorder;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.inputorder.OrderStatus;
import com.google.inject.Inject;

public class OutputOrderFactoryImpl implements OutputOrderFactory 
{
	private ConcurrentHashMap<String, Aggregation> symbolToAggregation;
	
	@Inject
	public OutputOrderFactoryImpl(ConcurrentHashMap<String, Aggregation> symbolToAggregation)
	{
		this.symbolToAggregation = symbolToAggregation;
	}
	
	@Override
	public OutputOrder newInstance(IoSession session, OrderStatus oldStatus, OrderStatus newStatus, String symbol, int quantity, int price, boolean buy, String clientId)
	{
		Aggregation globalAggregation = symbolToAggregation.get("global");
		Aggregation symbolAggregation = symbolToAggregation.get(symbol);
		return new OutputOrderImpl(oldStatus, newStatus, symbol, quantity, price, buy, clientId, session, globalAggregation, symbolAggregation);
	}

	@Override
	public OutputOrder newInstance() 
	{
		return new OutputOrderImpl();
	}

	@Override
	public OutputOrder invalidOrder(IoSession session) 
	{
		return new InvalidOutputOrderImpl(false, session);
	}
}
