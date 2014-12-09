package com.davidwales.matchingengine.responder;

import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.output.disruptor.ExecutedOrder;
import com.davidwales.matchingengine.priorityqueues.OrderStatus;

public class MessageResponderImpl implements MessageResponder 
{
	
	public void respond(ExecutedOrder order, IoSession session)
	{
		int buy = order.getBuy() ? 1 : 2;
		String fixResponse = order.getNewStatus() != OrderStatus.NEW ? "35=8|39=" + buy +"|" : "35=8|39=I" ;
		session.write(fixResponse);
	}
	
}
