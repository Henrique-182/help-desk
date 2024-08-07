package br.com.hd.controllers.chat.v1;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import br.com.hd.data.vo.auth.v1.AccountCredentialsVO;

@Controller
public class ChatController {

	@MessageMapping("/chat.addUser")
	@SendTo("/chat/public")
	public AccountCredentialsVO addUser(@Payload AccountCredentialsVO user) {
		
		return user;
	}

	
	@MessageMapping("/chat.sendMessage")
	@SendTo("/chat/public")
	public OutputMessage send(Message message) throws Exception {
	    String time = new SimpleDateFormat("HH:mm").format(new Date());
	    return new OutputMessage(message.getFrom(), message.getMessage(), time);
	}
	
}
