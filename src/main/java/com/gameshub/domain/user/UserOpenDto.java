package com.gameshub.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOpenDto {

    private String firstname;
    private String lastname;
    private LocalDateTime registeredDate;
    private String email;
}
