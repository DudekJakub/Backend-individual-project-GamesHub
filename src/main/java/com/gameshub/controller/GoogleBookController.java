package com.gameshub.controller;

import com.gameshub.domain.book.googleBook.GoogleBookDto;
import com.gameshub.domain.book.googleBook.GoogleBookListDto;
import com.gameshub.exception.GameNotFoundException;
import com.gameshub.exception.GoogleBookNotFoundException;
import com.gameshub.exception.GoogleBooksNotFoundException;
import com.gameshub.service.outern_api.GoogleBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/google_books")
public class GoogleBookController {

    private final GoogleBookService service;

    @GetMapping("/gameId/{gameId}")
    public ResponseEntity<Set<GoogleBookDto>> booksForGameByGameId(@PathVariable Long gameId) throws GoogleBooksNotFoundException, GameNotFoundException {
        return ResponseEntity.ok(service.fetchListOfGoogleBooksRelatedToGameByGameId(gameId));
    }

    @GetMapping("/gameName/{gameName}")
    public ResponseEntity<Set<GoogleBookDto>> booksForGameByGameName(@PathVariable String gameName) throws GoogleBooksNotFoundException {
        return ResponseEntity.ok(service.fetchListOfGoogleBooksRelatedToGameByGameName(gameName));
    }

    @GetMapping
    public ResponseEntity<GoogleBookListDto> booksByGoogleBookId(@RequestParam String googleBookId) throws GoogleBookNotFoundException {
        return ResponseEntity.ok(service.retrieveGoogleBookById(googleBookId));
    }
}
