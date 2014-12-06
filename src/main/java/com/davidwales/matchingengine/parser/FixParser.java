package com.davidwales.matchingengine.parser;

import com.davidwales.matchingengine.messages.DataType;
import com.davidwales.matchingengine.messages.TagValueMessage;
import com.google.inject.Inject;

public class FixParser implements Parser {
	
	public DataType[] tagToDataTypes;
	
	@Inject
	public FixParser(DataType[] tagToDataTypes)
	{
		this.tagToDataTypes = tagToDataTypes;
	}

	public void parseData(byte[] rawData, TagValueMessage parsedMessage) throws Exception
	{
		int tag = 0;
		int start = 0;
		int end = 1;
		
		for(int i = 0; i < rawData.length; i++ )
		{
			if(rawData[i] == 1 || rawData[i] == '\n')
			{
				end = i;
				processValue(rawData, start, end, tag, parsedMessage);
				start = end + 1;
			}
			else if(rawData[i] == '=')
			{
				end = i;
				tag = processTag(rawData, start, end);
				start = end + 1;
			}
		}
	}
	
	public int processTag(byte[] data, int start, int end) throws Exception
	{
		return processInt(data, start, end);
	}
	
	public void processValue(byte[] data, int start, int end, int tag, TagValueMessage parsedMessage) throws Exception
	{
		DataType dataTypeToParseTo = tagToDataTypes[tag];
		
		switch (dataTypeToParseTo) 
		{
			case LONG:
				long longVal = processLong(data, start, end);
				parsedMessage.putLong(longVal, tag);
				break;
			case INTEGER:
				int intVal = processInt(data, start, end);
				parsedMessage.putInt(intVal, tag);
				break;
			case BOOLEAN:
				boolean boolVal = processBoolean(data, start);
				parsedMessage.putBool(boolVal, tag);
				break;
			default:
				throw new IllegalStateException("Cannot find parser");
		}	
	}
	
	public long processLong(byte[] by, int start, int end)
	{
		long value = 0;
		for (int i = start; i < end; i++)
		{	
			value = 10*value + (by[i] -'0');
		}
		return value; 
	}
	
	public int processInt(byte[] by, int start, int end)
	{
		int value = 0;
		for (int i = start; i < end; i++)
		{	
			value = 10*value + (by[i] -'0');
		}
		return value; 
	}
	
	public boolean processBoolean(byte[] by, int index)
	{
		return (by[index] == 'Y');
	}
	
}
