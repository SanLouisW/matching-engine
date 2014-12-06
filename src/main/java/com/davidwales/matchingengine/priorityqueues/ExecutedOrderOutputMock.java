package com.davidwales.matchingengine.priorityqueues;

import com.davidwales.matchingengine.messages.TagValueMessage;

public class ExecutedOrderOutputMock implements ExecutedOrderOutput 
{

	@Override
	public void put(TagValueMessage message) 
	{
		System.out.println("order in = ");
		System.out.println(message);
		
	}

	@Override
	public void put(TagValueMessage buyMessage, TagValueMessage sellMessage) 
	{
		System.out.println("order matched = ");
		System.out.println(buyMessage);
		System.out.println(sellMessage);
	}

}
