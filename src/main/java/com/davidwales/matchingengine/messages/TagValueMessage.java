package com.davidwales.matchingengine.messages;

import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.priorityqueues.OrderStatus;

public interface TagValueMessage {
	
	public void putInt(int val, int tag);
	
	public void putLong(long val, int tag);
	
	public void putChar(char val, int tag);
	
	public char getChar(int tag); 
	
	public void putBool(boolean val, int tag);
	
	public int getInt(int tag);
	
	public long getLong(int tag);
	
	public char[] getString(int tag);
	
	public void putString(char[] val, int tag);
	
	public boolean getBool(int tag);
	
	public int getPrice();
	
	public boolean getBuy();
	
	public char[] getSymbol();
	
	public char[] getClientId();
	
	public int getQuantity();
	
	public DataType getTagDataType(int tag);
	
	public void setOrderStatus(OrderStatus status);
	
	public boolean isFilled(int decrement);
	
	public OrderStatus getOrderStatus();

	boolean getIsValid();
	
	public void validate();

	IoSession getAssociatedSession();

	void setAssociatedSession(IoSession associatedSession);
	

}
