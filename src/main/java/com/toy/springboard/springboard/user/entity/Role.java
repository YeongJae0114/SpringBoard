package com.toy.springboard.springboard.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    USER("USER"),
    ADMINISTRATOR("ADMIN");

    private final String key;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name(); // "ROLE_GUEST", "ROLE_WORKER", "ROLE_ADMINISTRATOR"
    }
}
