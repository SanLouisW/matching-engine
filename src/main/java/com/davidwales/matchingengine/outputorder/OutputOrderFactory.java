package com.davidwales.matchingengine.outputorder;

import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.inputorder.OrderStatus;
import com.davidwales.matchingengine.messages.TagValueMessage;

public interface OutputOrderFactory 
{
	public OutputOrder newInstance(IoSession Session, OrderStatus oldStatus, OrderStatus newStatus, String symbol, int quantity, int price, boolean buy, String clientId);	
	
	public OutputOrder newInstance();
	
	public OutputOrder invalidOrder(IoSession Session);
}
