package com.davidwales.matchingengine.output.disruptor;

import com.davidwales.matchingengine.priorityqueues.OrderStatus;

public class ExecutedOrderImpl implements ExecutedOrder
{

	OrderStatus oldStatus;
	
	OrderStatus newStatus;
	
	String symbol;
	
	int quantity;
	
	int price;
	
	boolean buy;
	
	ExecutedOrderImpl(){}
	
	ExecutedOrderImpl(OrderStatus oldStatus, OrderStatus newStatus, String symbol, int quantity, int price, boolean buy)
	{
		this.oldStatus = oldStatus;
		this.newStatus = newStatus;
		this.symbol = symbol;
		this.quantity = quantity;
		this.price = price;
	}

	@Override
	public OrderStatus getOldStatus() 
	{
		return this.oldStatus;
	}

	@Override
	public OrderStatus getNewStatus() 
	{
		return this.newStatus;
	}

	@Override
	public String getSymbol() 
	{
		return this.symbol;
	}

	@Override
	public int getQuantity() 
	{
		return this.quantity;
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
	
}
