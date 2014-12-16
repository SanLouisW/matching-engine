package com.davidwales.matchingengine;


import com.davidwales.matchingengine.input.di.DisruptorModule;
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
    	ApplicationStarter starter = injector.getInstance(ApplicationStarter.class);
    	
    	starter.start();
    }
}
