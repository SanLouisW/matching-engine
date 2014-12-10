package com.davidwales.matchingengine.responder;

import com.davidwales.matchingengine.output.disruptor.ExecutedOrder;
import com.davidwales.matchingengine.priorityqueues.OrderStatus;

public class MessageResponderImpl implements MessageResponder 
{
	
	public void respond(ExecutedOrder order)
	{
		String fixResponse;
		if(order.isValidOrder())
		{
			String buy = order.getBuy() ? "1" : "2";
			String tag39 = order.getNewStatus() == OrderStatus.FILLED ? buy : "I";
			fixResponse = "35=8|39=" + tag39 + "|38=" + order.getQuantity() + "|44=" + order.getPrice() + "|55=" + order.getSymbol() +"|";
		}
		else
		{
			fixResponse = "Incorrect format";
		}
		
		order.respond(fixResponse);
	}
	
}
