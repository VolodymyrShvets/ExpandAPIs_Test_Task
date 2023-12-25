package com.expandapis.vshvets295.service;

import com.expandapis.vshvets295.dto.JwtAuthenticationResponse;
import com.expandapis.vshvets295.dto.UserDto;
import com.expandapis.vshvets295.entities.Role;
import com.expandapis.vshvets295.entities.User;
import com.expandapis.vshvets295.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signUp(UserDto request) {
        log.info("[{}][signUp] -> signing up user with username: {}", AuthenticationService.class.getSimpleName(), request.getUsername());

        var user = User.builder()
                .username(request.getUsername()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();

        userRepository.save(user);
        log.info("[{}][signUp] -> user saved successfully", AuthenticationService.class.getSimpleName());

        var jwt = jwtService.generateToken(user);
        log.info("[{}][signUp] -> token generated for user: {}", AuthenticationService.class.getSimpleName(), user.getUsername());
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    public JwtAuthenticationResponse signIn(UserDto request) {
        log.info("[{}][signIn] -> signing in user with username: {}", AuthenticationService.class.getSimpleName(), request.getUsername());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        log.info("[{}][signIn] -> user loaded successfully", AuthenticationService.class.getSimpleName());

        var jwt = jwtService.generateToken(user);
        log.info("[{}][signIn] -> token generated for user: {}", AuthenticationService.class.getSimpleName(), user.getUsername());
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
