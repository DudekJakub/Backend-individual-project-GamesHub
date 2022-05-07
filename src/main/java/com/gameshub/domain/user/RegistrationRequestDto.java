package com.gameshub.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDto {

    private String loginName;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String repeatPassword;
}
