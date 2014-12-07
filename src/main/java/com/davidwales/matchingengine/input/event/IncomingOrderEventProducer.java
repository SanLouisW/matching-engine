package com.davidwales.matchingengine.input.event;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

@Singleton
public class IncomingOrderEventProducer implements DisruptorProducer<IncomingOrderEvent, byte[]> {
	
    private final RingBuffer<IncomingOrderEvent> ringBuffer;

    private final EventTranslatorOneArg<IncomingOrderEvent, byte[]> translator;
    
    @Inject
    public IncomingOrderEventProducer(Disruptor<IncomingOrderEvent> disruptor, EventTranslatorOneArg<IncomingOrderEvent, byte[]> translator)
    {
    	//TODO look into Guice to do this, currently hard to test
        this.ringBuffer = disruptor.getRingBuffer();
        this.translator = translator;
    }

    public void onData(byte[] bb)
    {   
        ringBuffer.publishEvent(translator, bb);
    }

	@Override
	public void run() {
 
		Configuration config = new Configuration();
		config.setHostname("localhost");
		config.setPort(9092);
		final SocketIOServer server = new SocketIOServer(config);
		server.addEventListener("msg", byte[].class, new DataListener<byte[]>() {
			 @Override
			 public void onData(SocketIOClient client, byte[] data, AckRequest ackRequest) {
				 client.sendEvent("msg", data);
			 }
		});
		
	}


}
