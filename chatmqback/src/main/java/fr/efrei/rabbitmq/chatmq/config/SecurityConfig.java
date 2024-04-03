package fr.efrei.rabbitmq.chatmq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Autowired
    private TokenFilter tokenFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                // .addFilterAt(tokenFilter, null)
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/register").permitAll()
                .requestMatchers("/**").authenticated()
                .anyRequest().denyAll())
                .build();
    }

}
