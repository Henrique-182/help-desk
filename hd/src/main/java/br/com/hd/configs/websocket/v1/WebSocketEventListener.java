package br.com.hd.configs.websocket.v1;

import java.util.Date;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

	private final SimpMessageSendingOperations messageTemplate;

	public WebSocketEventListener(SimpMessageSendingOperations messageTemplate) {
		this.messageTemplate = messageTemplate;
	}
	
	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
		
		//String username = (String) accessor.getSessionAttributes().get("username");
		
		accessor.getSessionAttributes().forEach((key, value) -> {
			System.out.println(key + " " + value);
		});
		
		messageTemplate.convertAndSend("/chat/public", ("Disconnected: "  + " at " + new Date()));
	}
	
}
