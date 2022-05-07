package com.gameshub.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AdminConfiguration {

    @Value("${admin.mail}")
    private String adminMail;

    @Value("${admin.name}")
    private String adminName;

    @Value("${info.app.company.name}")
    private String companyName;

    @Value("${info.app.company.email}")
    private String companyEmail;

    @Value("${info.app.company.phone}")
    private String companyPhone;
}
