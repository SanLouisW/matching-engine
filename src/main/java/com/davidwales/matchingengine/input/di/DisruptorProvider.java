/*package com.davidwales.matchingengine.input.di;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.davidwales.matchingengine.input.event.IncomingOrderEvent;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.Disruptor;

public class DisruptorProvider implements Provider<Disruptor<IncomingOrderEvent>>{
	
	private EventFactory<IncomingOrderEvent> factory;
	
	private Integer bufferSize = 8;
	
	private Executor executor;
	
	@Inject
	public DisruptorProvider(EventFactory<IncomingOrderEvent> factory){
		this.factory = factory;
		this.bufferSize = 5;
		this.executor = Executors.newCachedThreadPool();
	}
	
	public Disruptor<IncomingOrderEvent> get(){
		Disruptor<IncomingOrderEvent> disruptor = new Disruptor<>(factory, bufferSize, executor);
		return disruptor;     
	}

}
*/