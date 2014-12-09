package com.davidwales.matchingengine.output.disruptor.handlers;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class AggregatorServer extends WebSocketServer 
{
	
	//Guice injects the hashmap that the aggregator thread modifies here
	private ConcurrentHashMap<String, Aggregation> symbolToAggregation;
	
	public AggregatorServer( int port, ConcurrentHashMap<String, Aggregation> symbolToAggregation ) throws UnknownHostException 
	{
		super( new InetSocketAddress( port ) );
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
		conn.send("Global:" + 4);
	}	
	
	
	@Override
	public void onError( WebSocket conn, Exception ex ) 
	{
		ex.printStackTrace();
		if( conn != null ) {
			// some errors like port binding failed may not be assignable to a specific websocket
		}
	}
	

}
