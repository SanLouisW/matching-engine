package com.davidwales.matchingengine.input.event;

//TODO look into if this is really the right way of doing this
public interface DisruptorProducer<T, A> extends Runnable
{
	
    public void onData(A bb);
    
}
