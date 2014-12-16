package com.davidwales.matchingengine.outputorder;

import com.google.inject.Inject;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

public class ExecutedOrderOutputImpl implements ExecutedOrderOutput
{
	
	private final RingBuffer<OutputEvent> ringBuffer;
	private final EventTranslatorOneArg<OutputEvent, OutputOrder> translator;
	
	@Inject
	public ExecutedOrderOutputImpl(RingBuffer<OutputEvent> ringBuffer,EventTranslatorOneArg<OutputEvent, OutputOrder> translator)
	{
		this.ringBuffer = ringBuffer;
		this.translator = translator;
	}
	
	@Override
	public void put(OutputOrder executedOrder)
	{
		ringBuffer.publishEvent(translator, executedOrder);
	}
	
}
