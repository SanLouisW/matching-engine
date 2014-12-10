package com.davidwales.matchingengine.responder;

import com.davidwales.matchingengine.output.disruptor.ExecutedOrder;

public interface MessageResponder 
{
	public void respond(ExecutedOrder order);
}
