package fr.efrei.rabbitmq.chatmq.service;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;

@Service
public class Consumer {
    
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue queue;

    @RabbitListener(queues = "#{queue.getName()}")
    public void receiveMessage(Message message) throws IOException {
        System.out.println("Received <" + message + ">");
        
    }

}
