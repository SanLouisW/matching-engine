package com.davidwales.matchingengine.output.disruptor.handlers;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Set;

import org.java_websocket.WebSocket;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;


public class AggregatorWebSocketHandler extends WebSocketServer 
{


	public AggregatorWebSocketHandler( int port ) throws UnknownHostException {
		super( new InetSocketAddress( port ) );
	}

	public AggregatorWebSocketHandler( InetSocketAddress address ) {
		super( address );
	}
    
	@Override
	public void onOpen( WebSocket conn, ClientHandshake handshake ) {
		this.sendToAll( "new connection: " + handshake.getResourceDescriptor() );
		System.out.println( conn.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!" );
	}
	
	@Override
	public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
		this.sendToAll( conn + " has left the room!" );
		System.out.println( conn + " has left the room!" );
	}
	
	@Override
	public void onMessage( WebSocket conn, String message ) {
		this.sendToAll( message );
		System.out.println( conn + ": " + message );
	}	
	
	
	@Override
	public void onFragment( WebSocket conn, Framedata fragment ) {
		System.out.println( "received fragment: " + fragment );
	}
	
	@Override
	public void onError( WebSocket conn, Exception ex ) {
		ex.printStackTrace();
		if( conn != null ) {
			// some errors like port binding failed may not be assignable to a specific websocket
		}
	}
	
	public void sendToAll( String text ) {
		Collection<WebSocket> con = connections();
		synchronized ( con ) {
			for( WebSocket c : con ) {
				c.send( text );
			}
		}
	}

}
