package com.davidwales.matchingengine.translator;

import com.davidwales.matchingengine.outputorder.OutputEvent;
import com.davidwales.matchingengine.outputorder.OutputOrder;
import com.lmax.disruptor.EventTranslatorOneArg;

public class OutputEventTranslator implements EventTranslatorOneArg<OutputEvent, OutputOrder>
{

	@Override
	public void translateTo(OutputEvent event, long sequence, OutputOrder outputOrder) 
	{
		event.setExecutedOrder(outputOrder);
	}

}
