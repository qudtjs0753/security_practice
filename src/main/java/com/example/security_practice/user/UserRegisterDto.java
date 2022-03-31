package com.example.security_practice.user;

import lombok.*;

/**
 * @Author: kbs
 */

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRegisterDto {

    private String username;
    private String password;
}
