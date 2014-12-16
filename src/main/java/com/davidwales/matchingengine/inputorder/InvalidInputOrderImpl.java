package com.davidwales.matchingengine.inputorder;

import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.outputorder.ExecutedOrderOutput;
import com.davidwales.matchingengine.outputorder.OutputOrder;
import com.davidwales.matchingengine.outputorder.OutputOrderFactory;

public class InvalidInputOrderImpl extends InputOrderImpl 
{
	public InvalidInputOrderImpl(OrderBook<InputOrder> orderBook, IoSession session, int quantity, int price, String clientId, String symbol, boolean buy, ExecutedOrderOutput executedOrderOutput, OutputOrderFactory executedOrderFactory) 
	{
		super( orderBook, session, quantity, price, clientId, symbol, buy, executedOrderOutput, executedOrderFactory);
	}

	@Override
	public void place()
	{
		OutputOrder executedOrder = executedOrderFactory.invalidOrder(session);
		executedOrderOutput.put(executedOrder);
	}
}
