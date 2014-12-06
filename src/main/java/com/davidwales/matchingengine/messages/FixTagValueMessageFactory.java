package com.davidwales.matchingengine.messages;

import com.carrotsearch.hppc.IntByteOpenHashMap;
import com.carrotsearch.hppc.IntCharOpenHashMap;
import com.carrotsearch.hppc.IntIntOpenHashMap;
import com.carrotsearch.hppc.IntLongOpenHashMap;
import com.carrotsearch.hppc.IntObjectOpenHashMap;
import com.google.inject.Inject;

public class FixTagValueMessageFactory implements TagValueMessageFactory 
{

	DataType[] tagToDataTypes;
	
	@Inject
	public FixTagValueMessageFactory(DataType[] tagToDataTypes)
	{
		this.tagToDataTypes = tagToDataTypes;
	}
	
	@Override
	public TagValueMessage newInstance() 
	{
		
		IntIntOpenHashMap intMap = IntIntOpenHashMap.newInstance(400, 401);
		IntLongOpenHashMap longMap = IntLongOpenHashMap.newInstance(400, 401);
		IntObjectOpenHashMap<char[]> stringMap = IntObjectOpenHashMap.newInstance(400, 401);
		IntByteOpenHashMap byteMap = IntByteOpenHashMap.newInstance(400, 401);
		IntCharOpenHashMap charMap = IntCharOpenHashMap.newInstance(400, 401);
		
		return new FixTagValueMessage(intMap, longMap, stringMap, byteMap, charMap, tagToDataTypes);
		
	}

}
