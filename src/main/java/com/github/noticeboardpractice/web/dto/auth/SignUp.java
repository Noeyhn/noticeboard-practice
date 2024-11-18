package com.github.noticeboardpractice.web.dto.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignUp {

    private String email;
    private String password;
    private String name;

}
