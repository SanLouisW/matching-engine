package com.davidwales.matchingengine.network;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.davidwales.matchingengine.outputorder.Aggregation;
import com.google.inject.Inject;

public class AggregatorServer extends WebSocketServer 
{
	
	//Guice injects the hashmap that the aggregator thread modifies here
	private ConcurrentHashMap<String, Aggregation> symbolToAggregation;
	
	@Inject
	public AggregatorServer(ConcurrentHashMap<String, Aggregation> symbolToAggregation ) throws UnknownHostException 
	{
		super( new InetSocketAddress( 8887 ) );
		this.symbolToAggregation = symbolToAggregation;
	}

	public AggregatorServer( InetSocketAddress address ) 
	{
		super( address );
	}
    
	@Override
	public void onOpen( WebSocket conn, ClientHandshake handshake )
	{
		conn.send("Connection established");
	}
	
	@Override
	public void onClose( WebSocket conn, int code, String reason, boolean remote )
	{
	}
	
	
	@Override
	public void onMessage( WebSocket conn, String message ) 
	{
		Aggregation aggregation = symbolToAggregation.get(message);
		String jsonString = "{ \"fills\": \"" + aggregation.getFills() + "\", \"partials\": \""+ aggregation.getPartials() +"\", \"news\": \""+aggregation.getNews()+"\"}";
		conn.send(jsonString);
	}	
	
	
	@Override
	public void onError( WebSocket conn, Exception ex ) 
	{
		ex.printStackTrace();
		if( conn != null ) 
		{
			// some errors like port binding failed may not be assignable to a specific websocket
		}
	}
	

}
