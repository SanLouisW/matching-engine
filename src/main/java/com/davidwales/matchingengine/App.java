package com.davidwales.matchingengine;

import com.davidwales.matchingengine.input.di.DisruptorComposition;
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
    	DisruptorComposition disruptor = injector.getInstance(DisruptorComposition.class);

        byte[] byteArray = null;
        for (long l = 0; true; l++)
        {
            disruptor.onData(byteArray);
            Thread.sleep(1000);
        }   	
    }
}
