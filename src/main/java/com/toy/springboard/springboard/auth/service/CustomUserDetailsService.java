package com.toy.springboard.springboard.auth.service;

import com.toy.springboard.springboard.auth.CustomUserDetails;
import com.toy.springboard.springboard.user.exception.UserException;
import com.toy.springboard.springboard.user.entity.User;
import com.toy.springboard.springboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 여기서 username은 JWT의 subject 또는 usernameClaim으로 들어옴
        User user = userRepository.findByEmail(email).orElseThrow(UserException::userNotFound);
        return new CustomUserDetails(user);
    }
}
