package com.nocom.inst.websockets;

import java.io.Serializable;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/**
 * 
 * http://www.programmingforliving.com/2013/08/jsr-356-java-api-for-websocket-client-api.html
 * 
 * @author simplicity
 *
 */
@ClientEndpoint(decoders={JSONDecoder.class})
public class MemberClientEndpoint implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Session userSession = null;

	public MemberClientEndpoint(URI endpointURI) {
		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			container.connectToServer(this, endpointURI);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Callback hook for Connection open events.
	 * 
	 * @param userSession
	 *            the userSession which is opened.
	 */
	@OnOpen
	public void onOpen(Session userSession) {
		this.userSession = userSession;
	}
	
	@OnMessage
	public void onMessage(String message) {
		System.out.println(message);
	}

	/**
	 * Callback hook for Connection close events.
	 * 
	 * @param userSession
	 *            the userSession which is getting closed.
	 * @param reason
	 *            the reason for connection close
	 */
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		this.userSession = null;
	}


	/**
	 * Send a message.
	 * 
	 * @param user
	 * @param message
	 */
	public void sendMessage(String message) {
		this.userSession.getAsyncRemote().sendText(message);
	}



}
