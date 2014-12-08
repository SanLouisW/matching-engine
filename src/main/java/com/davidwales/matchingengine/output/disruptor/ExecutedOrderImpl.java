package com.davidwales.matchingengine.output.disruptor;

import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.messages.TagValueMessageFactory;
import com.google.inject.Inject;

public class ExecutedOrderImpl implements ExecutedOrder
{

	private TagValueMessage primaryOrder;
	
	private TagValueMessage secondaryOrder;
	
	ExecutedOrderImpl(TagValueMessage primaryOrder, TagValueMessage secondaryOrder)
	{
		this.primaryOrder = primaryOrder;
		this.secondaryOrder = secondaryOrder;
	}

	@Override
	public TagValueMessage getPrimaryOrder() 
	{
		return primaryOrder;
	}

	public void setPrimaryOrder(TagValueMessage primaryOrder) 
	{
		this.primaryOrder = primaryOrder;
	}

	@Override
	public TagValueMessage getSecondaryOrder() 
	{
		return secondaryOrder;
	}

	public void setSecondaryOrder(TagValueMessage secondaryOrder) 
	{
		this.secondaryOrder = secondaryOrder;
	}
	
}
