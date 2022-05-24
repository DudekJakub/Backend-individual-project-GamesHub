package com.gameshub.validator;

import com.gameshub.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookValidator {

    private final BookRepository bookRepository;

    public boolean validateNewBookAbsence(final String googleBookId) {
        return bookRepository.findAll()
                             .stream()
                             .anyMatch(book -> book.getGoogleId().equals(googleBookId));
    }
}
