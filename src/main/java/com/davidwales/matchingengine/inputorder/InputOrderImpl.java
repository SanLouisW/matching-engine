package com.davidwales.matchingengine.inputorder;

import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.outputorder.ExecutedOrderOutput;
import com.davidwales.matchingengine.outputorder.OutputOrder;
import com.davidwales.matchingengine.outputorder.OutputOrderFactory;

public class InputOrderImpl implements InputOrder 
{
	protected OrderBook<InputOrder> orderBook;
	
	protected IoSession session;
	
	protected int quantity;
	
	protected int price;
	
	protected String clientId;
	
	protected String symbol;
	
	protected boolean buy;
	
	protected ExecutedOrderOutput executedOrderOutput;
	
	protected final OutputOrderFactory executedOrderFactory;
	
	protected OrderStatus oldStatus = OrderStatus.NA;
	
	protected OrderStatus newStatus = OrderStatus.NEW;
	
	public InputOrderImpl(OrderBook<InputOrder> orderBook, IoSession session, int quantity, int price, String clientId, String symbol, boolean buy, ExecutedOrderOutput executedOrderOutput,  OutputOrderFactory executedOrderFactory)
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
		OutputOrder executedOrder = executedOrderFactory.newInstance(session, oldStatus, newStatus, symbol, quantity, price, buy, clientId);
		executedOrderOutput.put(executedOrder);
		orderBook.put(this);
		attemptMatch();
	}
	
	public void attemptMatch()
	{
		InputOrder buy = orderBook.buyQueuePeek();
		InputOrder sell = orderBook.sellQueuePeek();
		
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
		OutputOrder executedOrder = executedOrderFactory.newInstance(session, oldStatus, newStatus, symbol, quantity, price, buy, clientId);
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
