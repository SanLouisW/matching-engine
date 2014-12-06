package com.davidwales.matchingengine.input.handlers;

import java.io.OutputStream;

import com.davidwales.matchingengine.input.event.IncomingOrderEvent;
import com.google.inject.Inject;
import com.lmax.disruptor.EventHandler;

public class IncomingOrderPersister implements EventHandler<IncomingOrderEvent>{

	OutputStream outputStream;
	
	@Inject
	IncomingOrderPersister(OutputStream outputStream)
	{
		this.outputStream = outputStream;
	}
	
	public void onEvent(IncomingOrderEvent arg0, long arg1, boolean arg2) throws Exception 
	{
		outputStream.write(arg0.getRawData());
	}

}
