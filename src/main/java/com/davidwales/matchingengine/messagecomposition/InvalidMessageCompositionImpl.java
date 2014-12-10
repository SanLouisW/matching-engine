package com.davidwales.matchingengine.messagecomposition;

import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.output.disruptor.ExecutedOrder;
import com.davidwales.matchingengine.output.disruptor.ExecutedOrderFactory;
import com.davidwales.matchingengine.priorityqueues.ExecutedOrderOutput;
import com.davidwales.matchingengine.priorityqueues.OrderBook;

public class InvalidMessageCompositionImpl extends MessageCompositionImpl 
{
	public InvalidMessageCompositionImpl(OrderBook<MessageComposition> orderBook, IoSession session, int quantity, int price, String clientId, String symbol, boolean buy, ExecutedOrderOutput executedOrderOutput, ExecutedOrderFactory executedOrderFactory) 
	{
		super( orderBook, session, quantity, price, clientId, symbol, buy, executedOrderOutput, executedOrderFactory);
	}

	@Override
	public void place()
	{
		ExecutedOrder executedOrder = executedOrderFactory.invalidOrder(session);
		executedOrderOutput.put(executedOrder);
	}
}
