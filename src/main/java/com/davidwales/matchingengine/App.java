package com.davidwales.matchingengine;


import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.davidwales.matchingengine.input.di.DisruptorComposition;
import com.davidwales.matchingengine.input.di.DisruptorModule;
import com.davidwales.matchingengine.mina.InitialInputHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * 
 *
 */
public class App 
{
	
	public static void main( String[] args ) throws Exception
    {
    	Injector injector = Guice.createInjector(new DisruptorModule());
    	DisruptorComposition disruptor = injector.getInstance(DisruptorComposition.class);
    	
        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
        acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "US-ASCII" ))));
        acceptor.setHandler(  new InitialInputHandler() );
        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );
        acceptor.bind( new InetSocketAddress(9123) );
        
        disruptor.onData("44=10|55=aaa|54=1|38=10|".getBytes());
        disruptor.onData("44=10|55=aaa|54=2|38=5|".getBytes());
    }
}
