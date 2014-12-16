package com.davidwales.matchingengine.input.handlers;

import java.io.OutputStream;

import com.davidwales.matchingengine.inputorder.InputEvent;
import com.google.inject.Inject;
import com.lmax.disruptor.EventHandler;

public class IncomingOrderPersister implements EventHandler<InputEvent>{

	private OutputStream outputStream;
	
	@Inject
	IncomingOrderPersister(OutputStream outputStream)
	{
		this.outputStream = outputStream;
	}
	
	public void onEvent(InputEvent arg0, long arg1, boolean arg2) throws Exception 
	{
		outputStream.write(arg0.getRawData());
	}

}
