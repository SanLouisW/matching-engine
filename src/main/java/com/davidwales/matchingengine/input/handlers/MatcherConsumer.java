package com.davidwales.matchingengine.input.handlers;

import com.davidwales.matchingengine.input.event.IncomingOrderEvent;
import com.davidwales.matchingengine.messagecomposition.MessageComposition;
import com.davidwales.matchingengine.messagecomposition.MessageCompositionFactory;
import com.google.inject.Inject;
import com.lmax.disruptor.EventHandler;


public class MatcherConsumer implements EventHandler<IncomingOrderEvent> 
{

	private MessageCompositionFactory messageCompositionFactory;
	
	@Inject
	public MatcherConsumer( MessageCompositionFactory messageCompositionFactory)
	{
		this.messageCompositionFactory = messageCompositionFactory;
	}
	
	public void onEvent(IncomingOrderEvent arg0, long arg1, boolean arg2) throws Exception 
	{
		MessageComposition message = messageCompositionFactory.newInstance(arg0.getUnmarshalledMessage(), arg0.getSession());
		message.place();
	}

}