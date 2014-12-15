package com.davidwales.matchingengine.messagecomposition;

import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.output.disruptor.ExecutedOrder;
import com.davidwales.matchingengine.output.disruptor.ExecutedOrderFactory;
import com.davidwales.matchingengine.priorityqueues.ExecutedOrderOutput;
import com.davidwales.matchingengine.priorityqueues.OrderBook;
import com.davidwales.matchingengine.priorityqueues.OrderStatus;

public class MessageCompositionImpl implements MessageComposition 
{
	
	protected OrderBook<MessageComposition> orderBook;
	
	protected IoSession session;
	
	protected int quantity;
	
	protected int price;
	
	protected String clientId;
	
	protected String symbol;
	
	protected boolean buy;
	
	protected ExecutedOrderOutput executedOrderOutput;
	
	protected final ExecutedOrderFactory executedOrderFactory;
	
	protected OrderStatus oldStatus = OrderStatus.NA;
	
	protected OrderStatus newStatus = OrderStatus.NEW;
	
	public MessageCompositionImpl( OrderBook<MessageComposition> orderBook, IoSession session, int quantity, int price, String clientId, String symbol, boolean buy, ExecutedOrderOutput executedOrderOutput,  ExecutedOrderFactory executedOrderFactory)
	{
		this.orderBook = orderBook;
		this.session = session;
		this.quantity = quantity;
		this.price = price;
		this.clientId = clientId;
		this.symbol = symbol;
		this.buy = buy;
		this.executedOrderOutput = executedOrderOutput;
		this.executedOrderFactory = executedOrderFactory;
	}
	
	public void place()
	{
		ExecutedOrder executedOrder = executedOrderFactory.newInstance(session, oldStatus, newStatus, symbol, quantity, price, buy, clientId);
		executedOrderOutput.put(executedOrder);
		orderBook.put(this);
		attemptMatch();
	}
	
	public void attemptMatch()
	{
		
		MessageComposition buy = orderBook.buyQueuePeek();
		MessageComposition sell = orderBook.sellQueuePeek();
		
		if(buy != null && sell != null) 
		{
			int sellQuantity = sell.getQuantity();
			int buyQuantity = buy.getQuantity();
			
			buy.updateFilled(sellQuantity);
			sell.updateFilled(buyQuantity);
			
			if(buy.getNewStatus() == OrderStatus.FILLED || sell.getNewStatus() == OrderStatus.FILLED){
				attemptMatch();
			}
		}
	}
	
	public void updateFilled(int amountOfOppositeOrder)
	{
		int newAmount = this.quantity - amountOfOppositeOrder;
		this.oldStatus = newStatus;
		if(newAmount > 0)
		{
			this.newStatus = OrderStatus.PARTIAL;
			this.quantity = newAmount;
		}
		else
		{
			this.newStatus = OrderStatus.FILLED;
			this.quantity = 0;
			orderBook.remove(this);
		}
		ExecutedOrder executedOrder = executedOrderFactory.newInstance(session, oldStatus, newStatus, symbol, quantity, price, buy, clientId);
		executedOrderOutput.put(executedOrder);
	}
	
	@Override
	public int getPrice()
	{
		return this.price;
	}

	@Override
	public boolean getBuy() 
	{
		return this.buy;
	}

	@Override
	public int getQuantity() 
	{
		return this.quantity;
	}
	
	@Override
	public OrderStatus getOldStatus()
	{
		return this.newStatus;
	}
	
	@Override
	public OrderStatus getNewStatus()
	{
		return this.oldStatus;
	}
	
}
