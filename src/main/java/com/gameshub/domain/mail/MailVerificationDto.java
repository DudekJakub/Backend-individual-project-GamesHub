package com.gameshub.domain.mail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class MailVerificationDto {

    @JsonProperty(value = "smtpCheck")
    private boolean emailExists;
}
