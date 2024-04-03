package fr.efrei.rabbitmq.chatmq.model;

import java.util.Date;

public class WebSocketChatMessage {

	private String content;
	private String sender;
    private Date date;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
    

