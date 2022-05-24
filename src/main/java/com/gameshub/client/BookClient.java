package com.gameshub.client;

import com.gameshub.client.config.BookApiConfig;
import com.gameshub.domain.book.googleBook.GoogleBookDto;
import com.gameshub.domain.book.googleBook.GoogleBookListDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookClient.class);

    private final RestTemplate restTemplate;
    private final BookApiConfig bookApiConfig;

    private URI URI_BOOKS_FOR_GAME_LIST(final String gameName) {
        return UriComponentsBuilder.fromHttpUrl(bookApiConfig.getBooksApiEndpoint())
                .queryParam("key", bookApiConfig.getBooksAppKey())
                .queryParam("q", gameName)
                .build()
                .encode()
                .toUri();
    }

    private URI URI_BOOK_BY_ID(final String googleBookId) {
        return UriComponentsBuilder.fromHttpUrl(bookApiConfig.getBooksApiEndpoint())
                .queryParam("key", bookApiConfig.getBooksAppKey())
                .queryParam("q", googleBookId)
                .build()
                .encode()
                .toUri();
    }

    public Optional<GoogleBookListDto> getBooksList(final String gameName) {
        URI url = URI_BOOKS_FOR_GAME_LIST(gameName);
        try {
            GoogleBookListDto booksResponse = restTemplate.getForObject(url, GoogleBookListDto.class);
            return Optional.ofNullable(booksResponse);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public Optional<GoogleBookListDto> getBookById(final String googleBookId) {
        URI url = URI_BOOK_BY_ID(googleBookId);
        try {
            GoogleBookListDto bookResponse = restTemplate.getForObject(url, GoogleBookListDto.class);
            return Optional.ofNullable(bookResponse);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Optional.empty();
        }
    }
}
