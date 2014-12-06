package com.davidwales.matchingengine.input.event;

public interface DisruptorProducer<T, A> extends Runnable{
	
    public void onData(A bb);
    
}
