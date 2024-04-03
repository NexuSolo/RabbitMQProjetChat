package fr.efrei.rabbitmq.chatmq.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import fr.efrei.rabbitmq.chatmq.model.User;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserRepository {

    private List<User> users = new ArrayList<>();

    public User findByUsername(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

    public boolean save(User user) {
        log.info("List of users: {}", users);
        log.info("Saving user {}", user);
        return users.add(user);
    }
    
}
