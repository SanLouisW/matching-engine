package com.davidwales.matchingengine.output.disruptor;

import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.priorityqueues.OrderStatus;

public interface ExecutedOrder 
{

	public OrderStatus getOldStatus();
	
	public OrderStatus getNewStatus();
	
	public String getSymbol();
	
	public int getQuantity();
	
	public int getPrice();
	
	public boolean getBuy();
	
	public String getClientId();
	
	public IoSession getSession();
	
	public boolean isValidOrder();
	
	public void respond(String response);

}
