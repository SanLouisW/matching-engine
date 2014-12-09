package com.davidwales.matchingengine.input.di;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.java_websocket.server.WebSocketServer;

import com.carrotsearch.hppc.ObjectObjectOpenHashMap;
import com.davidwales.matchingengine.input.di.annotations.Aggregator;
import com.davidwales.matchingengine.input.di.annotations.Matcher;
import com.davidwales.matchingengine.input.di.annotations.OutputPersister;
import com.davidwales.matchingengine.input.di.annotations.Persister;
import com.davidwales.matchingengine.input.di.annotations.Unmarshaller;
import com.davidwales.matchingengine.input.event.DisruptorProducer;
import com.davidwales.matchingengine.input.event.IncomingOrderEvent;
import com.davidwales.matchingengine.input.event.IncomingOrderEventFactory;
import com.davidwales.matchingengine.input.event.IncomingOrderEventProducer;
import com.davidwales.matchingengine.input.handlers.IncomingOrderPersister;
import com.davidwales.matchingengine.input.handlers.IncomingOrderUnmarshaller;
import com.davidwales.matchingengine.input.handlers.MatcherConsumer;
import com.davidwales.matchingengine.messages.DataType;
import com.davidwales.matchingengine.messages.FixTagValueMessageFactory;
import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.messages.TagValueMessageFactory;
import com.davidwales.matchingengine.mina.NetworkInputHandler;
import com.davidwales.matchingengine.output.disruptor.ExecuteOrderFactoryImpl;
import com.davidwales.matchingengine.output.disruptor.ExecutedOrder;
import com.davidwales.matchingengine.output.disruptor.ExecutedOrderFactory;
import com.davidwales.matchingengine.output.disruptor.MatchingEventOutputDisruptor;
import com.davidwales.matchingengine.output.disruptor.OrderOutputEvent;
import com.davidwales.matchingengine.output.disruptor.OrderOutputEventFactory;
import com.davidwales.matchingengine.output.disruptor.OrderOutputProducer;
import com.davidwales.matchingengine.output.disruptor.handlers.Aggregation;
import com.davidwales.matchingengine.output.disruptor.handlers.AggregatorServer;
import com.davidwales.matchingengine.output.disruptor.handlers.OrderAggregation;
import com.davidwales.matchingengine.output.disruptor.handlers.OutputOrderAggregator;
import com.davidwales.matchingengine.output.disruptor.handlers.OutputOrderPersister;
import com.davidwales.matchingengine.output.disruptor.handlers.aggregators.AggregatorTranslator;
import com.davidwales.matchingengine.output.disruptor.handlers.aggregators.AggregatorTranslatorImpl;
import com.davidwales.matchingengine.parser.Parser;
import com.davidwales.matchingengine.parser.TagValueParser;
import com.davidwales.matchingengine.priorityqueues.ExecutedOrderOutput;
import com.davidwales.matchingengine.priorityqueues.InstrumentMatcherImpl;
import com.davidwales.matchingengine.priorityqueues.InstrumentsMatcher;
import com.davidwales.matchingengine.priorityqueues.OrderBook;
import com.davidwales.matchingengine.priorityqueues.OrderBookImpl;
import com.davidwales.matchingengine.translator.IncomingOrderTranslator;
import com.davidwales.matchingengine.translator.OutputOrderTranslator;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.dsl.Disruptor;

public class DisruptorModule extends AbstractModule 
{
	
	@Override 
	protected void configure() 
	{
		//RingBuffer dependency injection
		bind(TagValueMessageFactory.class).to(FixTagValueMessageFactory.class);
		bind(ExecutedOrderFactory.class).to(ExecuteOrderFactoryImpl.class);
		bind(ExecutedOrderOutput.class).to(MatchingEventOutputDisruptor.class);
		bind(AggregatorTranslator.class).to(AggregatorTranslatorImpl.class);
		bind(IoHandlerAdapter.class).to(NetworkInputHandler.class);
		bind(new TypeLiteral<EventFactory<IncomingOrderEvent>>(){}).to(IncomingOrderEventFactory.class);
		
		//Here be dragons
		bind(new TypeLiteral<EventFactory<OrderOutputEvent>>(){}).to(OrderOutputEventFactory.class);
		bind(new TypeLiteral<EventHandler<OrderOutputEvent>>(){}).annotatedWith(Aggregator.class).to(OutputOrderPersister.class);
		bind(new TypeLiteral<EventHandler<OrderOutputEvent>>(){}).annotatedWith(OutputPersister.class).to(OutputOrderAggregator.class);
		bind(new TypeLiteral<EventFactory<IncomingOrderEvent>>(){}).to(IncomingOrderEventFactory.class);
		bind(new TypeLiteral<Parser<TagValueMessage>>(){}).to(new TypeLiteral<TagValueParser<TagValueMessage>>(){});
		bind(new TypeLiteral<OrderBook<TagValueMessage>>(){}).to(OrderBookImpl.class);
		bind(new TypeLiteral<InstrumentsMatcher<TagValueMessage>>(){}).to(InstrumentMatcherImpl.class);
		bind(new TypeLiteral<EventHandler<IncomingOrderEvent>>(){}).annotatedWith(Persister.class).to(IncomingOrderPersister.class);
		bind(new TypeLiteral<EventHandler<IncomingOrderEvent>>(){}).annotatedWith(Unmarshaller.class).to(IncomingOrderUnmarshaller.class);
		bind(new TypeLiteral<EventHandler<IncomingOrderEvent>>(){}).annotatedWith(Matcher.class).to(MatcherConsumer.class);
		//
		
		//Producer dependency injection 
		bind(new TypeLiteral<EventTranslatorOneArg<IncomingOrderEvent, byte[]>>(){}).to(IncomingOrderTranslator.class);
		bind(new TypeLiteral<EventTranslatorOneArg<OrderOutputEvent, ExecutedOrder>>(){}).to(OutputOrderTranslator.class);
		
		bind(new TypeLiteral<DisruptorProducer<IncomingOrderEvent, byte[]>>(){}).to(IncomingOrderEventProducer.class);
		bind(new TypeLiteral<DisruptorProducer<OrderOutputEvent, ExecutedOrder>>(){}).to(OrderOutputProducer.class);
	}
	
	@Singleton
	@Inject
	@Provides
	@SuppressWarnings("unchecked")
	public Disruptor<IncomingOrderEvent> getDisruptor(EventFactory<IncomingOrderEvent> factory, @Persister EventHandler<IncomingOrderEvent> persister, @Unmarshaller EventHandler<IncomingOrderEvent> unmarshaller, @Matcher EventHandler<IncomingOrderEvent> matcher) 
	{
		Disruptor<IncomingOrderEvent> disruptor = new Disruptor<>(factory, 1024, Executors.newCachedThreadPool());
		disruptor.handleEventsWith(persister);
		disruptor.handleEventsWith(unmarshaller).then(matcher);
		disruptor.start();
		return disruptor;     
	}
	
	@Singleton
	@Inject
	@Provides
	@SuppressWarnings("unchecked")
	public Disruptor<OrderOutputEvent> getDisruptorOrderOutputEvent(EventFactory<OrderOutputEvent> factory, @Aggregator EventHandler<OrderOutputEvent> aggregator, @OutputPersister EventHandler<OrderOutputEvent> persister) 
	{
		Disruptor<OrderOutputEvent> disruptor = new Disruptor<>(factory, 1024, Executors.newCachedThreadPool());
		disruptor.handleEventsWith(aggregator).then(persister);
		disruptor.start();
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
	@Provides
	@Singleton
	public ConcurrentHashMap<String, IoSession> getClientIdToSessionMap()
	{
		ConcurrentHashMap<String, IoSession> clientIdToSessionMap = new ConcurrentHashMap<String, IoSession>();
		return clientIdToSessionMap;
	}
	
	@Inject
	@Provides
	public WebSocketServer getWebSocketServer(ConcurrentHashMap<String, Aggregation> symbolToAggregation) throws UnknownHostException 
	{
		AggregatorServer server = new AggregatorServer(8887, symbolToAggregation);
		server.start();
		return server;	
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
	public PriorityQueue<TagValueMessage> getPriorityQueue()
	{
		return new PriorityQueue<TagValueMessage>(100, new TagValueMessageComparator());
	}
	
	@Singleton
	@Provides
	@Inject
	public ObjectObjectOpenHashMap<String, OrderBook<TagValueMessage>> getSymbolToOrderBook(OrderBook<TagValueMessage> orderBook)
	{
		ObjectObjectOpenHashMap<String, OrderBook<TagValueMessage>> symbolToOrderBook = ObjectObjectOpenHashMap.newInstance(1, 2);
		String symbol = "aaa";
		
		symbolToOrderBook.put(symbol, orderBook);
		return symbolToOrderBook;
	}
	
	@Singleton
	@Provides
	@Inject
	public IoAcceptor getIoAcceptor(IoHandlerAdapter ioHandlerAdapter) throws IOException
	{
        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "US-ASCII" ))));
        acceptor.setHandler(  ioHandlerAdapter );
        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );
        acceptor.bind( new InetSocketAddress(9123) );
		return acceptor;

	}
	
	@Singleton
	@Provides
	public DataType[] getTagToDataTypes()
	{
		DataType[] dataTypes = new DataType[500];
		Arrays.fill(dataTypes, DataType.NA);
		dataTypes[38] = DataType.INTEGER;
		dataTypes[55] = DataType.STRING;
		dataTypes[54] = DataType.CHAR;
		dataTypes[44] = DataType.INTEGER;
		return dataTypes;
	}
	
	
}
