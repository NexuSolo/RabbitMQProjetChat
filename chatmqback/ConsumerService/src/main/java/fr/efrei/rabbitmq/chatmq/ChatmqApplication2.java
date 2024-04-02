package fr.efrei.rabbitmq.chatmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ChatmqApplication2 {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ChatmqApplication2.class, args);
	}

}
