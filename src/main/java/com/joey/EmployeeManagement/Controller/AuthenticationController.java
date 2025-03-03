package com.joey.EmployeeManagement.Controller;

import com.joey.EmployeeManagement.Services.AuthenticationService;
import com.joey.EmployeeManagement.auth.AuthenticationRequest;
import com.joey.EmployeeManagement.auth.AuthenticationResponse;
import com.joey.EmployeeManagement.auth.RegisterRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@ModelAttribute RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate/form")
    public String authenticateForm(@ModelAttribute AuthenticationRequest request, HttpSession session) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/login_page?error=true";
        }
    }

    @PostMapping("/authenticate/api")
    public ResponseEntity<AuthenticationResponse> authenticateApi(@ModelAttribute AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }
}
