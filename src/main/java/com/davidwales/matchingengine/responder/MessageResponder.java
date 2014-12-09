package com.davidwales.matchingengine.responder;

import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.output.disruptor.ExecutedOrder;

public interface MessageResponder 
{
	public void respond(ExecutedOrder order, IoSession session);
}
