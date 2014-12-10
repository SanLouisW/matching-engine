package com.davidwales.matchingengine.output.disruptor;

import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.priorityqueues.OrderStatus;

public class ExecutedOrderImpl implements ExecutedOrder
{

	private OrderStatus oldStatus;
	
	private OrderStatus newStatus;
	
	private String symbol;
	
	private String clientId;
	
	private int quantity;
	
	private int price;
	
	private boolean buy;
	
	protected boolean isValid;
	
	protected IoSession session;
	
	ExecutedOrderImpl(){}
	
	public ExecutedOrderImpl(OrderStatus oldStatus, OrderStatus newStatus, String symbol, int quantity, int price, boolean buy, String clientId, IoSession session)
	{
		this.oldStatus = oldStatus;
		this.newStatus = newStatus;
		this.symbol = symbol;
		this.quantity = quantity;
		this.price = price;
		this.clientId = clientId;
		this.isValid = true;
		this.session = session;
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

	@Override
	public IoSession getSession() {
		return this.session;
	}


	@Override
	public boolean isValidOrder() {
		return this.isValid;
	}

	@Override
	public void respond()
	{
		String fixResponse;
		String buy = this.buy ? "1" : "2";
		String tag39 = this.newStatus == OrderStatus.FILLED ? buy : "I";
		fixResponse = "35=8|39=" + tag39 + "|38=" + this.quantity + "|44=" + this.price + "|55=" + this.symbol +"|";
		this.session.write(fixResponse);
	}

}
