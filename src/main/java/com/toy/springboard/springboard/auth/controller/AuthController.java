package com.toy.springboard.springboard.auth.controller;

import com.toy.springboard.springboard.global.response.ApiResponse;
import com.toy.springboard.springboard.auth.dto.LoginRequest;
import com.toy.springboard.springboard.auth.dto.SignupRequest;
import com.toy.springboard.springboard.auth.dto.TokenResponse;
import com.toy.springboard.springboard.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/api/auth/login")
    public ResponseEntity<ApiResponse<Void>> login(@Valid @RequestBody LoginRequest loginRequest){
        TokenResponse res = authService.login(loginRequest);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + res.getAccessToken())
                .header("Refresh-Token", res.getRefreshToken())
                .body(ApiResponse.success()); //
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@Valid @RequestBody SignupRequest signupRequest){
        authService.signup(signupRequest);
        return ResponseEntity.ok().body(ApiResponse.success());
    }



}
