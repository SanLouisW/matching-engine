package com.davidwales.matchingengine.output.disruptor;

import com.davidwales.matchingengine.messages.TagValueMessage;

public interface ExecutedOrder 
{

	public TagValueMessage getPrimaryOrder(); 
	
	public TagValueMessage getSecondaryOrder(); 

}
