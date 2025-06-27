package ru.kpfu.itis.security;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/error");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        request.setAttribute("error.status_code", HttpStatus.UNAUTHORIZED.value());
        dispatcher.forward(request, response);
    }
}