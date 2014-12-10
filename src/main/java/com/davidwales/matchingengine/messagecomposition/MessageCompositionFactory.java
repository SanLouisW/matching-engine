package com.davidwales.matchingengine.messagecomposition;

import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.messages.TagValueMessage;

public interface MessageCompositionFactory 
{
	public MessageComposition newInstance(TagValueMessage tagValueMessage, IoSession session);

}
