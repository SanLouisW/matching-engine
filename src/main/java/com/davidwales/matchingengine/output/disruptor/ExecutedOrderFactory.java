package com.davidwales.matchingengine.output.disruptor;

import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.priorityqueues.OrderStatus;

public interface ExecutedOrderFactory 
{
	public ExecutedOrder newInstance(IoSession Session, OrderStatus oldStatus, OrderStatus newStatus, String symbol, int quantity, int price, boolean buy, String clientId);	
	
	public ExecutedOrder newInstance();
	
	public ExecutedOrder invalidOrder(IoSession Session);
}
