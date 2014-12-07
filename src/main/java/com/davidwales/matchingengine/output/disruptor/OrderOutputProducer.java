package com.davidwales.matchingengine.output.disruptor;

import com.davidwales.matchingengine.input.event.DisruptorProducer;
import com.google.inject.Inject;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

//TODO generify this with the input disruptor, currently duplicated code
public class OrderOutputProducer implements DisruptorProducer<OrderOutputEvent, ExecutedOrder>
{

    private final RingBuffer<OrderOutputEvent> ringBuffer;

    private final EventTranslatorOneArg<OrderOutputEvent, ExecutedOrder> translator;
    
	@Inject
    public OrderOutputProducer(Disruptor<OrderOutputEvent> disruptor, EventTranslatorOneArg<OrderOutputEvent, ExecutedOrder> translator)
    {
    	//TODO look into Guice to do this, currently hard to test
        this.ringBuffer = disruptor.getRingBuffer();
        this.translator = translator;
    }
    
	@Override
	public void run() 
	{
		
	}

	@Override
	public void onData(ExecutedOrder executedOrder) 
	{
		ringBuffer.publishEvent(translator, executedOrder);
	}

}
