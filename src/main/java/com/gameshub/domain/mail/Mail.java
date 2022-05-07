package com.gameshub.domain.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Mail {

    private String mailTo;
    private String subject;
    private String message;
}
