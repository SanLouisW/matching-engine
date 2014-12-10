package com.davidwales.matchingengine.output.disruptor;

import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.priorityqueues.OrderStatus;

public class ExecuteOrderFactoryImpl implements ExecutedOrderFactory 
{
	
	@Override
	public ExecutedOrder newInstance(IoSession session, OrderStatus oldStatus, OrderStatus newStatus, String symbol, int quantity, int price, boolean buy, String clientId)
	{
		return new ExecutedOrderImpl(oldStatus, newStatus, symbol, quantity, price , buy, clientId, session);
	}

	@Override
	public ExecutedOrder newInstance() 
	{
		return new ExecutedOrderImpl();
	}

	@Override
	public ExecutedOrder invalidOrder(IoSession session) 
	{
		return new InvalidOrderImpl(false, session);
	}
}
