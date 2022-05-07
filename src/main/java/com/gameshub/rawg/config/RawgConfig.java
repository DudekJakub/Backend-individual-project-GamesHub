package com.gameshub.rawg.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RawgConfig {

    @Value("${rawg.api.endpoint.prod}")
    private String rawgApiEndpoint;

    @Value("${rawg.app.key}")
    private String rawgAppKey;
}
