package com.toy.springboard.springboard.auth.service;

import com.toy.springboard.springboard.auth.token.JwtProvider;
import com.toy.springboard.springboard.auth.token.RefreshTokenRepository;
import com.toy.springboard.springboard.user.exception.UserException;
import com.toy.springboard.springboard.auth.dto.LoginRequest;
import com.toy.springboard.springboard.auth.dto.SignupRequest;
import com.toy.springboard.springboard.auth.dto.TokenResponse;
import com.toy.springboard.springboard.user.entity.Role;
import com.toy.springboard.springboard.user.entity.User;
import com.toy.springboard.springboard.user.repository.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImp implements AuthService{
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(NoSuchElementException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw UserException.userNotFound();
        }

        String accessToken = jwtProvider.createAccessToken(user.getEmail(), List.of(user.getRole().getAuthority()));
        String refreshToken = jwtProvider.createRefreshToken(user.getEmail());

        refreshTokenRepository.save(user.getId().toString(), refreshToken, 1000L * 60 * 60 * 24 * 7); // 7일

        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public void signup(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw UserException.emailExists();
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        User user = User.builder()
                .name(signupRequest.getName())
                .email(signupRequest.getEmail())
                .password(encodedPassword)
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }

    @Override
    public void logout() {

    }
}
