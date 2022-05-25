package com.gameshub.controller;

import com.gameshub.domain.user.UserOpenDto;
import com.gameshub.exception.*;
import com.gameshub.facade.BookFacade;
import com.gameshub.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/books")
public class BookController {

    private final BookFacade bookFacade;
    private final BookService bookService;

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @GetMapping("/{bookId}/users")
    public ResponseEntity<Set<UserOpenDto>> bookUsers(@PathVariable Long bookId) throws BookNotFoundException {
        return ResponseEntity.ok(bookFacade.fetchBookUsers(bookId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @PostMapping("/{googleBookId}/{userId}")
    public ResponseEntity<Boolean> addToUserMemorizedBookList(@PathVariable String googleBookId, @PathVariable Long userId) throws UserNotFoundException, GoogleBookNotFoundException, BookAlreadyMemorizedException {
        return ResponseEntity.ok(bookFacade.persistAndAddToUserMemorizedBookList(googleBookId, userId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER') and @userValidator.hasValidatedAccount()")
    @DeleteMapping("/{bookId}/{userId}")
    public ResponseEntity<Boolean> removeFromUserMemorizedBookList(@PathVariable Long bookId, @PathVariable Long userId) throws UserNotFoundException, BookNotFoundException,
            BookNotMemorizedException {
        return ResponseEntity.ok(bookService.removeFromUserMemorizedBookList(bookId, userId));
    }
}
