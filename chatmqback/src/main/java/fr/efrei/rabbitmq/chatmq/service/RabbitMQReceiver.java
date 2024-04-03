package fr.efrei.rabbitmq.chatmq.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import fr.efrei.rabbitmq.chatmq.model.WebSocketChatMessage;
@Component
@RabbitListener(queues = "rabbitmq.queue", id = "listener")
public class RabbitMQReceiver {
    @RabbitHandler
    public void receiver(WebSocketChatMessage message) {
        System.out.println("Received message: " + message.getContent());
    }
}