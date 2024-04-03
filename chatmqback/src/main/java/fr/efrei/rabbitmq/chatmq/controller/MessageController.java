package fr.efrei.rabbitmq.chatmq.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;

import fr.efrei.rabbitmq.chatmq.model.WebSocketChatMessage;

public class MessageController {

    @MessageMapping("/sendMessage")
	@SendTo("/topic/public")
	public WebSocketChatMessage sendMessage(@Payload WebSocketChatMessage webSocketChatMessage) {
		return webSocketChatMessage;
	}
}
