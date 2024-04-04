package fr.efrei.rabbitmq.chatmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import fr.efrei.rabbitmq.chatmq.model.Message;
import fr.efrei.rabbitmq.chatmq.service.Producer;

@RestController
public class MessageController {

    @Autowired
    private Producer producer;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody Message message) {
        producer.sendMessage(message);
        return ResponseEntity.ok().build();
    }

}
