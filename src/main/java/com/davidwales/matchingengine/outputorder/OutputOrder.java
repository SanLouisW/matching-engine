package com.davidwales.matchingengine.outputorder;

import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.inputorder.OrderStatus;

public interface OutputOrder 
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
	
	public void respond();
	
	public void aggregate();

}
