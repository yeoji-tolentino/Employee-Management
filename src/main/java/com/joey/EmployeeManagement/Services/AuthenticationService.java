package com.joey.EmployeeManagement.Services;

import com.joey.EmployeeManagement.Config.JwtService;
import com.joey.EmployeeManagement.Model.Role;
import com.joey.EmployeeManagement.Model.User;
import com.joey.EmployeeManagement.Repository.UserRepository;
import com.joey.EmployeeManagement.auth.AuthenticationRequest;
import com.joey.EmployeeManagement.auth.AuthenticationResponse;
import com.joey.EmployeeManagement.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request){
        var user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.ADMIN);

        repository.save(user);
        var jwtToken = jwtService.generateToken((UserDetails) user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var customer = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken((UserDetails) customer);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
