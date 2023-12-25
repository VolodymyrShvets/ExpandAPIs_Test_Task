package com.expandapis.vshvets295.controller;

import com.expandapis.vshvets295.dto.JwtAuthenticationResponse;
import com.expandapis.vshvets295.dto.UserDto;
import com.expandapis.vshvets295.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/add")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody UserDto request) {
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody UserDto request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }
}
