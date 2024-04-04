package fr.efrei.rabbitmq.chatmq.service;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.efrei.rabbitmq.chatmq.model.Message;

@Service
public class Producer {
    
    @Autowired
	private RabbitTemplate template;
    
    @Autowired
    private Queue queue;

    public void sendMessage(Message message) {
        this.template.convertAndSend(queue.getName(), message.toString());
    }

}
