package fr.efrei.rabbitmq.chatmq.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Autowired
    private TokenFilter tokenFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(tokenFilter, SecurityContextHolderFilter.class)
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("auth/register").permitAll()
                .anyRequest().denyAll())
                .build();
    }

    @Bean
    UserDetailsService users() {
        List<UserDetails> users = new ArrayList<>();
        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
