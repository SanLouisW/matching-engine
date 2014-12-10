package com.davidwales.matchingengine.messages;

import org.apache.mina.core.session.IoSession;

import com.carrotsearch.hppc.IntByteOpenHashMap;
import com.carrotsearch.hppc.IntCharOpenHashMap;
import com.carrotsearch.hppc.IntIntOpenHashMap;
import com.carrotsearch.hppc.IntLongOpenHashMap;
import com.carrotsearch.hppc.IntObjectOpenHashMap;
import com.davidwales.matchingengine.priorityqueues.OrderStatus;

public class FixTagValueMessage implements TagValueMessage
{
	final private IntIntOpenHashMap intMap;
	
	final private IntLongOpenHashMap longMap;
	
	final private IntObjectOpenHashMap<char[]> stringMap;
	
	final private IntByteOpenHashMap byteMap;
	
	final private IntCharOpenHashMap charMap;
	
	final public DataType[] tagToDataTypes;
	
	private volatile boolean isValid;
	
	private IoSession associatedSession;
	
	OrderStatus status;

	public FixTagValueMessage(IntIntOpenHashMap intMap, IntLongOpenHashMap longMap, IntObjectOpenHashMap<char[]> stringMap, IntByteOpenHashMap byteMap, IntCharOpenHashMap charMap, DataType[] tagToDataTypes)
	{
		this.intMap = intMap;
		this.longMap = longMap;
		this.stringMap = stringMap;
		this.byteMap = byteMap;
		this.charMap = charMap;
		this.tagToDataTypes = tagToDataTypes;
		this.status = OrderStatus.NEW;
		this.isValid = false;
	}

	@Override
	public boolean getIsValid()
	{
		return this.isValid;
	}
	
	@Override
	public void putInt(int val, int tag) 
	{
		intMap.put(tag, val);
	}

	@Override
	public void putLong(long val, int tag) 
	{
		longMap.put(tag, val);
	}

	
	@Override
	public char getChar(int tag) 
	{
		return charMap.get(tag);
	}

	
	@Override
	public void putChar(char val, int tag) 
	{
		charMap.put(tag, val);
	}
	
	@Override
	public void putString(char[] val, int tag) 
	{
		stringMap.put(tag, val);
	}

	@Override
	public void putBool(boolean val, int tag) 
	{
		byteMap.put(tag, val ? (byte)1 : (byte)0 );
	}

	@Override
	public int getInt(int tag) 
	{
		return intMap.get(tag);
	}

	@Override
	public long getLong(int tag) 
	{
		return longMap.get(tag);
	}

	@Override
	public char[] getString(int tag) 
	{
		return stringMap.get(tag);
	}

	@Override
	public boolean getBool(int tag) 
	{
		return byteMap.get(tag) == 1;
	}

	@Override
	public int getPrice() 
	{
		return intMap.get(44);
	}
	
	@Override
	public int getQuantity() 
	{
		return intMap.get(38);
	}

	@Override
	public boolean getBuy() 
	{
		return charMap.get(54) == '1';
	}

	@Override
	public char[] getSymbol() 
	{
		return stringMap.get(55);
	}

	@Override 
	public OrderStatus getOrderStatus()
	{
		return this.status;
	}
	@Override 
	public void setOrderStatus(OrderStatus status)
	{
		this.status = status;
	}
	
	@Override
	public char[] getClientId()
	{
		return stringMap.get(109);
	}
	
	@Override 
	public boolean isFilled(int decrement)
	{
		if(decrement > 0)
		{
			int quantity = intMap.get(38);
			intMap.put(38, quantity-decrement);
			return quantity-decrement <= 0;
		}
		else
		{
			return false;
		}
	}

	@Override
	public DataType getTagDataType(int tag) 
	{
		return tagToDataTypes[tag];
	}
	
	@Override
	public void validate()
	{
		this.isValid = intMap.get(38) != 0 &&
					   intMap.get(44) != 0 &&
					   stringMap.get(109)  != null &&
					   charMap.get(54) != '\u0000' &&
		               stringMap.get(55) != null;
	}

	@Override
	public IoSession getAssociatedSession() 
	{
		return associatedSession;
	}

	@Override
	public void setAssociatedSession(IoSession associatedSession) 
	{
		this.associatedSession = associatedSession;
	}
}
