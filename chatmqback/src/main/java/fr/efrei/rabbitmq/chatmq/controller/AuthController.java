package fr.efrei.rabbitmq.chatmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;

import fr.efrei.rabbitmq.chatmq.model.User;
import fr.efrei.rabbitmq.chatmq.service.AuthentificationService;

@RestController
@RequestMapping("/auth/")
public class AuthController {

    @Autowired
    private AuthentificationService authentificationService;

    @PostMapping("register")
    public ResponseEntity<?> newUser(@RequestBody User user) {
        try {
            if (!authentificationService.register(user)) {
                if (!authentificationService.login(user)) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
            String token = authentificationService.getToken(user.getUsername());
            return ResponseEntity.ok()
                    .body("{\"token\": \"" + token + "\", \"username\": \"" + user.getUsername() + "\"" + "}");
        } catch (JOSEException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
