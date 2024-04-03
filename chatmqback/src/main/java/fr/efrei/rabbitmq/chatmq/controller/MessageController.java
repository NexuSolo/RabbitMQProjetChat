package fr.efrei.rabbitmq.chatmq.controller;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import fr.efrei.rabbitmq.chatmq.model.Message;

@RestController
public class MessageController {

    @Autowired
	private RabbitTemplate template;

    @Autowired
    private Queue queue;


    @PostMapping("/send")
    public void sendMessage(@RequestBody Message message) {
        this.template.convertAndSend(queue.getName(), message.toString());
    }

    // @RabbitListener(queues = "chat.queue")
    // public void listen(Message message) {
    //     System.out.println("Received a message: " + message.getContent());
    // }
}
