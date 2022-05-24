package com.gameshub.client.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class BookApiConfig {

    @Value("${google.books.api.endpoint.prod}")
    private String booksApiEndpoint;

    @Value("${google.books.app.key}")
    private String booksAppKey;
}
