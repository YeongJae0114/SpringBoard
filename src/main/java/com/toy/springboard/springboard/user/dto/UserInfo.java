package com.toy.springboard.springboard.user.dto;

import com.toy.springboard.springboard.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private Long id;
    private String name;

    public static UserInfo formEntity(User user){
        return new UserInfo(user.getId(), user.getName());
    }
}
