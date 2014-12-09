package com.davidwales.matchingengine.messages;

import com.davidwales.matchingengine.priorityqueues.OrderStatus;

public interface TagValueMessage {
	
	void putInt(int val, int tag);
	
	void putLong(long val, int tag);
	
	void putChar(char val, int tag);
	
	public char getChar(int tag); 
	
	void putBool(boolean val, int tag);
	
	int getInt(int tag);
	
	long getLong(int tag);
	
	char[] getString(int tag);
	
	void putString(char[] val, int tag);
	
	boolean getBool(int tag);
	
	int getPrice();
	
	boolean getBuy();
	
	char[] getSymbol();
	
	int getQuantity();
	
	DataType getTagDataType(int tag);
	
	void setOrderStatus(OrderStatus status);
	
	public boolean isFilled(int decrement);
	
	OrderStatus getOrderStatus();
	

}
