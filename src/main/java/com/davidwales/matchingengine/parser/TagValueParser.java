package com.davidwales.matchingengine.parser;

import com.davidwales.matchingengine.messages.DataType;
import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.output.disruptor.ExecutedOrder;
import com.davidwales.matchingengine.priorityqueues.OrderStatus;

public class TagValueParser<T extends TagValueMessage> implements Parser<T> 
{
	
	public void parseData(byte[] rawData, T parsedMessage)
	{
		int tag = 0;
		int start = 0;
		int end = 1;
		try
		{
			for(int i = 0; i < rawData.length; i++ )
			{
				if(rawData[i] == '|' || rawData[i] == '\n')
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
		catch(Exception exception)
		{
		
			//TODO implement proper exception handling
			System.out.println("exception parsing message");
		}
		
		parsedMessage.validate();
	}
	
	public int processTag(byte[] data, int start, int end) 
	{
		return processInt(data, start, end);
	}
	
	public void processValue(byte[] data, int start, int end, int tag, T parsedMessage)
	{
		DataType dataTypeToParseTo = parsedMessage.getTagDataType(tag);
		
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
			case STRING:
				char[] stringVal = processString(data, start, end);
				parsedMessage.putString(stringVal, tag);
				break;
			case CHAR:
				char charVal = processChar(data[start]);
				parsedMessage.putChar(charVal, tag);
				break;
			default:
				System.out.println("Can't find datatype for this tag");
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
	
	public char processChar(byte by)
	{
		return (char)by;
	}
	
	
	public char[] processString(byte[] by, int start, int end)
	{
		char[] value = new char[end - start];
		for (int i = start, j = 0; i < end; i++, j++)
		{	
			value[j] = processChar(by[i]);
		}
		return value; 
	}
	
	public boolean processBoolean(byte[] by, int index)
	{
		return (by[index] == 'Y');
	}
	
}
