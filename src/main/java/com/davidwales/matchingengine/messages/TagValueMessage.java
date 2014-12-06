package com.davidwales.matchingengine.messages;

public interface TagValueMessage {
	
	void putInt(int val, int tag);
	
	void putLong(long val, int tag);
	
	void putChar(char[] val, int tag);
	
	void putBool(boolean val, int tag);
	
	int getInt(int tag);
	
	long getLong(int tag);
	
	char[] getString(int tag);
	
	char getChar(int tag);
	
	boolean getBool(int tag);
	
	int getPrice();
	
	boolean getBuy();
	
	char[] getSymbol();
	
	DataType getTagDataType(int tag);

}
