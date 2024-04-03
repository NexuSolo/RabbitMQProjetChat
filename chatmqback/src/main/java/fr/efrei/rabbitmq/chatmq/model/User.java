package fr.efrei.rabbitmq.chatmq.model;

import lombok.Data;

@Data
public class User {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }
    
}
