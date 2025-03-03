package com.joey.EmployeeManagement.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import java.io.IOException;

public class AccessDeniedHandler {

    @Bean
    public org.springframework.security.web.access.AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl() {
            private final Logger logger = LoggerFactory.getLogger(this.getClass());

            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                logger.error("403 Forbidden: " + request.getRequestURI() + " - Reason: " + accessDeniedException.getMessage());
                super.handle(request, response, accessDeniedException);
            }
        };
    }
}
