package fr.efrei.rabbitmq.chatmq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import fr.efrei.rabbitmq.chatmq.service.AuthentificationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenFilter extends OncePerRequestFilter  {

    @Autowired
    private AuthentificationService authentificationService;

    @SuppressWarnings("null")
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String path = request.getRequestURI();
        if (path.equals("/auth/register")) {
            try {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                System.out.println(auth);
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
                    response.setStatus(401);
                }
            } else {
                response.setStatus(401);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }

}
