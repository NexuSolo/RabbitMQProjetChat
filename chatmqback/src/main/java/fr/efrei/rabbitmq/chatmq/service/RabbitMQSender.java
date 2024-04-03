package fr.efrei.rabbitmq.chatmq.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.efrei.rabbitmq.chatmq.model.WebSocketChatMessage;

@Service
public class RabbitMQSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    public void send(WebSocketChatMessage message) {
        rabbitTemplate.convertAndSend(queue.getName(), message);
    }
}