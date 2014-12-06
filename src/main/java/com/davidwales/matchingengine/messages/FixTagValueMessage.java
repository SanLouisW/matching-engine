package com.davidwales.matchingengine.messages;

import com.carrotsearch.hppc.IntByteOpenHashMap;
import com.carrotsearch.hppc.IntCharOpenHashMap;
import com.carrotsearch.hppc.IntIntOpenHashMap;
import com.carrotsearch.hppc.IntLongOpenHashMap;
import com.carrotsearch.hppc.IntObjectOpenHashMap;

public class FixTagValueMessage implements TagValueMessage
{
	final private IntIntOpenHashMap intMap;
	
	final private IntLongOpenHashMap longMap;
	
	final private IntObjectOpenHashMap<char[]> stringMap;
	
	final private IntByteOpenHashMap byteMap;
	
	final private IntCharOpenHashMap charMap;
	
	final public DataType[] tagToDataTypes;

	public FixTagValueMessage(IntIntOpenHashMap intMap, IntLongOpenHashMap longMap, IntObjectOpenHashMap<char[]> stringMap, IntByteOpenHashMap byteMap, IntCharOpenHashMap charMap, DataType[] tagToDataTypes)
	{
		this.intMap = intMap;
		this.longMap = longMap;
		this.stringMap = stringMap;
		this.byteMap = byteMap;
		this.charMap = charMap;
		this.tagToDataTypes = tagToDataTypes;
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
	public void putChar(char[] val, int tag) 
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
	public boolean getBuy() 
	{
		return stringMap.get(54)[0] == 1;
	}

	@Override
	public char[] getSymbol() 
	{
		return stringMap.get(55);
	}

	@Override
	public char getChar(int tag) 
	{
		return charMap.get(tag);
	}

	@Override
	public DataType getTagDataType(int tag) 
	{
		return tagToDataTypes[tag];
	}
	
}
