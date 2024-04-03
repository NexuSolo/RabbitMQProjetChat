package fr.efrei.rabbitmq.chatmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.efrei.rabbitmq.chatmq.model.WebSocketChatMessage;
import fr.efrei.rabbitmq.chatmq.service.RabbitMQSender;

@RestController
@RequestMapping(value = "/rabbitmq")
public class MessageController {

    @Autowired
    RabbitMQSender rabbitMQSender;
    @PostMapping(value = "/send")
    public String producer(@RequestBody WebSocketChatMessage message) {
        rabbitMQSender.send(message);
        return "Message sent to the RabbitMQ Queue Successfully";
    }
}
