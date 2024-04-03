package fr.efrei.rabbitmq.chatmq.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketChatMessage {
	@JsonProperty
	private String content;
	@JsonProperty
	private String sender;
	@JsonProperty
    private Date date;
}
    

