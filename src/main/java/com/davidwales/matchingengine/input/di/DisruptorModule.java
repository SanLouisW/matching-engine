package com.davidwales.matchingengine.input.di;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.concurrent.Executors;

import com.davidwales.matchingengine.input.di.annotations.Matcher;
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
import com.davidwales.matchingengine.messages.FixTagValueMessage;
import com.davidwales.matchingengine.messages.FixTagValueMessageFactory;
import com.davidwales.matchingengine.messages.TagValueMessage;
import com.davidwales.matchingengine.messages.TagValueMessageFactory;
import com.davidwales.matchingengine.parser.Parser;
import com.davidwales.matchingengine.parser.TagValueParser;
import com.davidwales.matchingengine.translator.IncomingOrderTranslator;
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
		bind(new TypeLiteral<TagValueMessageFactory<TagValueMessage>>(){}).to(FixTagValueMessageFactory.class);
		bind(new TypeLiteral<EventFactory<IncomingOrderEvent>>(){}).to(IncomingOrderEventFactory.class);
		bind(new TypeLiteral<Parser<TagValueMessage>>(){}).to(TagValueParser.class);
		bind(new TypeLiteral<EventHandler<IncomingOrderEvent>>(){}).annotatedWith(Persister.class).to(IncomingOrderPersister.class);
		bind(new TypeLiteral<EventHandler<IncomingOrderEvent>>(){}).annotatedWith(Unmarshaller.class).to(IncomingOrderUnmarshaller.class);
		bind(new TypeLiteral<EventHandler<IncomingOrderEvent>>(){}).annotatedWith(Matcher.class).to(MatcherConsumer.class);
		//Producer dependency injection 
		bind(new TypeLiteral<EventTranslatorOneArg<IncomingOrderEvent, byte[]>>(){}).to(IncomingOrderTranslator.class);
		bind(new TypeLiteral<DisruptorProducer<IncomingOrderEvent, byte[]>>(){}).to(IncomingOrderEventProducer.class);
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
	@Provides
	public OutputStream getOuputStream() throws FileNotFoundException
	{
		File file = new File("c://dev//data.txt");
		FileOutputStream outputStream = new FileOutputStream(file);
		return outputStream;
	}
	
	@Singleton
	@Provides
	public DataType[] getTagToDataTypes()
	{
		DataType[] dataTypes = new DataType[500];
		Arrays.fill(dataTypes, DataType.NA);
		
		return dataTypes;
	}
	
	
}
