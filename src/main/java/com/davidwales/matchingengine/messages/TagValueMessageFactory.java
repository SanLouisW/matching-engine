package com.davidwales.matchingengine.messages;

public interface TagValueMessageFactory<T extends TagValueMessage> {

	T newInstance();
	
}
