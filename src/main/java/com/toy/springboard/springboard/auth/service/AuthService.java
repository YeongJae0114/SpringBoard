package com.toy.springboard.springboard.auth.service;

import com.toy.springboard.springboard.auth.dto.LoginRequest;
import com.toy.springboard.springboard.auth.dto.SignupRequest;
import com.toy.springboard.springboard.auth.dto.TokenResponse;

public interface AuthService {
    TokenResponse login(LoginRequest loginRequest);

    void signup(SignupRequest signupRequest);

    void logout();
}
