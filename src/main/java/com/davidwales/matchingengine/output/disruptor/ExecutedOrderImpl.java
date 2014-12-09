package com.davidwales.matchingengine.output.disruptor;

import com.davidwales.matchingengine.priorityqueues.OrderStatus;

public class ExecutedOrderImpl implements ExecutedOrder
{

	public OrderStatus oldStatus;
	
	public OrderStatus newStatus;
	
	public String symbol;
	
	public String clientId;
	
	public int quantity;
	
	public int price;
	
	public boolean buy;
	
	ExecutedOrderImpl(){}
	
	ExecutedOrderImpl(OrderStatus oldStatus, OrderStatus newStatus, String symbol, int quantity, int price, boolean buy, String clientId)
	{
		this.oldStatus = oldStatus;
		this.newStatus = newStatus;
		this.symbol = symbol;
		this.quantity = quantity;
		this.price = price;
		this.clientId = clientId;
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

	@Override
	public String getClientId() 
	{
		return this.clientId;
	}
	
}
