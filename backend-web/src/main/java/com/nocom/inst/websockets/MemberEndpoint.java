package com.nocom.inst.websockets;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;

import com.nocom.inst.model.Member;

@ServerEndpoint(value = "/members", encoders={JSONEncoder.class})
public class MemberEndpoint {
	
	@Inject
	private Logger logger;

    @Inject
    private SessionRegistry sessionRegistry;

    @OnOpen
    public void open(Session session, EndpointConfig conf) {
        sessionRegistry.add(session);
    }

    @OnClose
    public void close(Session session, CloseReason reason) {
        sessionRegistry.remove(session);
    }

    public void send(@Observes Member member) {
        sessionRegistry.getAll().forEach(
        		
        		session -> {session.getAsyncRemote().sendObject(member);
        		System.out.println(String.format("WS - sending message to client %s for Member %s", session.getQueryString(),member.getName()));
        		});
    }
    
    @OnError
    public void processError(Throwable t) {
    	logger.error(t.getMessage());
        t.printStackTrace();
    }
}