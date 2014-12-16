package com.davidwales.matchingengine.input.di;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.java_websocket.server.WebSocketServer;

import com.carrotsearch.hppc.ObjectObjectOpenHashMap;
import com.davidwales.matchingengine.input.di.annotations.Aggregator;
import com.davidwales.matchingengine.input.di.annotations.Matcher;
import com.davidwales.matchingengine.input.di.annotations.OutputPersister;
import com.davidwales.matchingengine.input.di.annotations.Persister;
import com.davidwales.matchingengine.input.di.annotations.Responder;
import com.davidwales.matchingengine.input.di.annotations.Unmarshaller;
import com.davidwales.matchingengine.input.handlers.IncomingOrderPersister;
import com.davidwales.matchingengine.input.handlers.IncomingOrderUnmarshaller;
import com.davidwales.matchingengine.input.handlers.MatcherConsumer;
import com.davidwales.matchingengine.inputorder.InputEvent;
import com.davidwales.matchingengine.inputorder.InputEventFactory;
import com.davidwales.matchingengine.inputorder.InputOrder;
import com.davidwales.matchingengine.inputorder.InputOrderComparator;
import com.davidwales.matchingengine.inputorder.InputOrderFactory;
import com.davidwales.matchingengine.inputorder.InputOrderFactoryImpl;
import com.davidwales.matchingengine.inputorder.OrderBook;
import com.davidwales.matchingengine.inputorder.OrderBookImpl;
import com.davidwales.matchingengine.messages.DataType;
import com.davidwales.matchingengine.messages.FixTagValueMessageFactory;
import com.davidwales.matchingengine.messages.Parser;
import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.messages.TagValueMessageFactory;
import com.davidwales.matchingengine.messages.TagValueParser;
import com.davidwales.matchingengine.network.AggregatorServer;
import com.davidwales.matchingengine.network.NetworkInputConfiguration;
import com.davidwales.matchingengine.network.NetworkInputConfigurationImpl;
import com.davidwales.matchingengine.network.NetworkInputHandler;
import com.davidwales.matchingengine.output.disruptor.handlers.OrderOutputResponder;
import com.davidwales.matchingengine.output.disruptor.handlers.OutputOrderAggregator;
import com.davidwales.matchingengine.output.disruptor.handlers.OutputOrderPersister;
import com.davidwales.matchingengine.outputorder.Aggregation;
import com.davidwales.matchingengine.outputorder.ExecutedOrderOutput;
import com.davidwales.matchingengine.outputorder.ExecutedOrderOutputImpl;
import com.davidwales.matchingengine.outputorder.OrderAggregation;
import com.davidwales.matchingengine.outputorder.OutputEvent;
import com.davidwales.matchingengine.outputorder.OutputEventFactory;
import com.davidwales.matchingengine.outputorder.OutputOrder;
import com.davidwales.matchingengine.outputorder.OutputOrderFactory;
import com.davidwales.matchingengine.outputorder.OutputOrderFactoryImpl;
import com.davidwales.matchingengine.translator.InputEventTranslator;
import com.davidwales.matchingengine.translator.OutputEventTranslator;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.EventTranslatorTwoArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

public class DisruptorModule extends AbstractModule 
{
	
	@Override 
	protected void configure() 
	{
		
		//Network input DI
		bind(IoHandler.class).to(NetworkInputHandler.class);
		bind(NetworkInputConfiguration.class).to(NetworkInputConfigurationImpl.class);
		bind(IoAcceptor.class).to(NioSocketAcceptor.class);
		
		//
		
		//Web Socket Server DI
		bind(WebSocketServer.class).to(AggregatorServer.class);
		//
		
		//Input Disruptor Factories which are injected in the input disruptor provider declared below DI		
		bind(new TypeLiteral<EventFactory<InputEvent>>(){}).to(InputEventFactory.class);
		bind(TagValueMessageFactory.class).to(FixTagValueMessageFactory.class);
		//
		
		//Input Disruptor handlers which are injected in the input disruptor provider declared below DI
		bind(new TypeLiteral<EventHandler<InputEvent>>(){}).annotatedWith(Persister.class).to(IncomingOrderPersister.class);
		bind(new TypeLiteral<EventHandler<InputEvent>>(){}).annotatedWith(Unmarshaller.class).to(IncomingOrderUnmarshaller.class);
		bind(new TypeLiteral<EventHandler<InputEvent>>(){}).annotatedWith(Matcher.class).to(MatcherConsumer.class);
		//
		
		//Output Disruptor Factories which are injected in the output disruptor provider declared below DI		
		bind(new TypeLiteral<EventFactory<OutputEvent>>(){}).to(OutputEventFactory.class);
		bind(OutputOrderFactory.class).to(OutputOrderFactoryImpl.class);
		//
		
		//Output Disruptor handlers which are injected in the output disruptor provider declared below DI
		bind(new TypeLiteral<EventHandler<OutputEvent>>(){}).annotatedWith(Responder.class).to(OrderOutputResponder.class);
		bind(new TypeLiteral<EventHandler<OutputEvent>>(){}).annotatedWith(Aggregator.class).to(OutputOrderPersister.class);
		bind(new TypeLiteral<EventHandler<OutputEvent>>(){}).annotatedWith(OutputPersister.class).to(OutputOrderAggregator.class);
		//
		
		//Input order factory DI, used to turn a message into an enriched input order for matching purposes
		bind(InputOrderFactory.class).to(InputOrderFactoryImpl.class);
		bind(ExecutedOrderOutput.class).to(ExecutedOrderOutputImpl.class);
		
		//Here be dragons
		bind(new TypeLiteral<Parser<TagValueMessage>>(){}).to(new TypeLiteral<TagValueParser<TagValueMessage>>(){});
		bind(new TypeLiteral<OrderBook<InputOrder>>(){}).to(OrderBookImpl.class);
		//
		
		//Translator dependency injection 
		bind(new TypeLiteral<EventTranslatorTwoArg<InputEvent, byte[], IoSession>>(){}).to(InputEventTranslator.class);
		bind(new TypeLiteral<EventTranslatorOneArg<OutputEvent, OutputOrder>>(){}).to(OutputEventTranslator.class);
		
	}
	
	
	//Network input DI
	@Provides
	public IoFilterAdapter getFilter()
	{
		return new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "US-ASCII" )));
	}
	
	@Provides
	public SocketAddress getSocketAddress()
	{
		//TODO maintain port
		int port = 9123;
		return new InetSocketAddress(port);
	}
	
	//Input Disruptor DI
	@Singleton
	@Inject
	@Provides
	@SuppressWarnings("unchecked")
	public Disruptor<InputEvent> getDisruptor(EventFactory<InputEvent> factory, @Persister EventHandler<InputEvent> persister, @Unmarshaller EventHandler<InputEvent> unmarshaller, @Matcher EventHandler<InputEvent> matcher) 
	{
		Disruptor<InputEvent> disruptor = new Disruptor<>(factory, 1024, Executors.newCachedThreadPool());
		disruptor.handleEventsWith(persister);
		disruptor.handleEventsWith(unmarshaller).then(matcher);
		return disruptor;     
	}
	
	//Used to inject the object used to publish events to the ring buffer into classes that need it
	@Singleton
	@Inject
	@Provides
	public RingBuffer<InputEvent> getRingBuffer(Disruptor<InputEvent> disruptor)
	{
		return disruptor.getRingBuffer();
	}
	//Used to inject the object used to publish events to the ring buffer into classes that need it
	@Singleton
	@Inject
	@Provides
	public RingBuffer<OutputEvent> getOutputRingBuffer(Disruptor<OutputEvent> disruptor)
	{
		return disruptor.getRingBuffer();
	}
	
	@Singleton
	@Inject
	@Provides
	@SuppressWarnings("unchecked")
	public Disruptor<OutputEvent> getDisruptorOrderOutputEvent(EventFactory<OutputEvent> factory, @Aggregator EventHandler<OutputEvent> aggregator, @OutputPersister EventHandler<OutputEvent> persister, @Responder EventHandler<OutputEvent> responder) 
	{
		Disruptor<OutputEvent> disruptor = new Disruptor<>(factory, 1024, Executors.newCachedThreadPool());
		disruptor.handleEventsWith(responder);
		disruptor.handleEventsWith(aggregator).then(persister);
		return disruptor;     
	}
	
	@Provides
	@Singleton
	public ConcurrentHashMap<String, Aggregation> getSymbolToAggregation()
	{
		ConcurrentHashMap<String, Aggregation> symbolToAggregation = new ConcurrentHashMap<String, Aggregation>();
		symbolToAggregation.put("global", new OrderAggregation());
		symbolToAggregation.put("aaa", new OrderAggregation());
		return symbolToAggregation;
	}
	
	@Singleton
	@Provides
	public OutputStream getOuputStream() throws FileNotFoundException
	{
		File file = new File("c://dev//data.txt");
		FileOutputStream outputStream = new FileOutputStream(file);
		return outputStream;
	}
	
	@Provides
	public PriorityQueue<InputOrder> getPriorityQueue()
	{
		return new PriorityQueue<InputOrder>(100, new InputOrderComparator());
	}
	
	@Singleton
	@Provides
	@Inject
	public ObjectObjectOpenHashMap<String, OrderBook<InputOrder>> getSymbolToOrderBook(OrderBook<InputOrder> orderBook)
	{
		ObjectObjectOpenHashMap<String, OrderBook<InputOrder>> symbolToOrderBook = ObjectObjectOpenHashMap.newInstance(1, 2);
		String symbol = "aaa";
		
		symbolToOrderBook.put(symbol, orderBook);
		return symbolToOrderBook;
	}
	
	
	@Singleton
	@Provides
	public DataType[] getTagToDataTypes()
	{
		DataType[] dataTypes = new DataType[500];
		Arrays.fill(dataTypes, DataType.NA);
		dataTypes[109] = DataType.STRING;
		dataTypes[38] = DataType.INTEGER;
		dataTypes[55] = DataType.STRING;
		dataTypes[54] = DataType.CHAR;
		dataTypes[44] = DataType.INTEGER;
		return dataTypes;
	}
	
	
}
