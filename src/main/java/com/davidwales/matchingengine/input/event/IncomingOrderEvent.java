package com.davidwales.matchingengine.input.event;

import com.davidwales.matchingengine.messages.TagValueMessage;

public class IncomingOrderEvent {

	private final byte[] rawData;
	
	private final TagValueMessage unmarshalledMessage;

	public IncomingOrderEvent(TagValueMessage unmarshalledMessage){
		rawData = new byte[1000];
		this.unmarshalledMessage = unmarshalledMessage;
	}
	
	public byte[] getRawData() {
		return rawData;
	}

	public void setRawData(byte[] rawData) {
		//Arrays.fill(this.rawData, null);
	}

	public TagValueMessage getUnmarshalledMessage() {
		return unmarshalledMessage;
	}
	
	
}
