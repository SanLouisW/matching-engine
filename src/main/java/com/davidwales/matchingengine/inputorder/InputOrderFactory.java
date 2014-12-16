package com.davidwales.matchingengine.inputorder;

import org.apache.mina.core.session.IoSession;

import com.davidwales.matchingengine.messages.TagValueMessage;

public interface InputOrderFactory 
{
	public InputOrder newInstance(TagValueMessage tagValueMessage, IoSession session);

}
