package fr.efrei.rabbitmq.chatmq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import fr.efrei.rabbitmq.chatmq.service.AuthentificationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenFilter extends OncePerRequestFilter  {

    @Autowired
    private AuthentificationService authentificationService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String path = request.getRequestURI();
        log.info("Request to {}", path);
        if (path.equals("/auth/register")) {
            try {
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(500);
            }
            return;
        }
        try {
            String token = request.getHeader("Authorization");
            if (token != null) {
                if (authentificationService.verifyToken(token)) {
                    filterChain.doFilter(request, response);
                } else {
                    System.err.println("Invalid token");
                    response.setStatus(401);
                }
            } else {
                System.err.println("No token");
                response.setStatus(401);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }

}
