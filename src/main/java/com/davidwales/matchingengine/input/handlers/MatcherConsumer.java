package com.davidwales.matchingengine.input.handlers;

import com.davidwales.matchingengine.inputorder.InputEvent;
import com.davidwales.matchingengine.inputorder.InputOrder;
import com.davidwales.matchingengine.inputorder.InputOrderFactory;
import com.google.inject.Inject;
import com.lmax.disruptor.EventHandler;


public class MatcherConsumer implements EventHandler<InputEvent> 
{

	private InputOrderFactory messageCompositionFactory;
	
	@Inject
	public MatcherConsumer( InputOrderFactory messageCompositionFactory)
	{
		this.messageCompositionFactory = messageCompositionFactory;
	}
	
	public void onEvent(InputEvent arg0, long arg1, boolean arg2) throws Exception 
	{
		InputOrder message = messageCompositionFactory.newInstance(arg0.getUnmarshalledMessage(), arg0.getSession());
		message.place();
	}

}