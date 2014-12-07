package com.davidwales.matchingengine.translator;

import com.davidwales.matchingengine.output.disruptor.ExecutedOrder;
import com.davidwales.matchingengine.output.disruptor.OrderOutputEvent;
import com.lmax.disruptor.EventTranslatorOneArg;

public class OutputOrderTranslator implements EventTranslatorOneArg<OrderOutputEvent, ExecutedOrder>
{

	@Override
	public void translateTo(OrderOutputEvent event, long sequence, ExecutedOrder arg0) 
	{
		event.setExecutedOrder(arg0);
	}

}
