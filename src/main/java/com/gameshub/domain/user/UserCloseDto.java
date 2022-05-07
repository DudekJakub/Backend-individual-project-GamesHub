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
public class UserCloseDto {

    private Long id;
    private String loginName;
    private AppUserRole appUserRole;
    private LocalDateTime registeredDate;
    private String email;
    private String firstname;
    private String lastname;
    private boolean active;
    private boolean verified;
}
