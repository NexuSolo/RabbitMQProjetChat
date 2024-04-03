package fr.efrei.rabbitmq.chatmq.model;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import static java.time.ZoneOffset.UTC;
import static lombok.AccessLevel.PACKAGE;

@Value
@AllArgsConstructor(access = PACKAGE)
public class Message {
	
	String content;
    ZonedDateTime date;
	String sender;

	@JsonCreator
	public static Message message(@JsonProperty("content") String content, @JsonProperty("date") ZonedDateTime date, @JsonProperty("sender") String sender) {
		return new Message(content, date, sender);
	}

	public static Message textMessage(@NonNull String content) {
		return new Message(content, ZonedDateTime.now(UTC), "test");
	}
}
    

