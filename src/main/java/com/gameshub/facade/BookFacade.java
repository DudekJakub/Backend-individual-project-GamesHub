package com.gameshub.facade;

import com.gameshub.domain.book.Book;
import com.gameshub.domain.book.googleBook.GoogleBookDto;
import com.gameshub.domain.user.UserOpenDto;
import com.gameshub.exception.BookAlreadyMemorizedException;
import com.gameshub.exception.BookNotFoundException;
import com.gameshub.exception.GoogleBookNotFoundException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.mapper.book.BookMapper;
import com.gameshub.mapper.user.UserMapper;
import com.gameshub.repository.BookRepository;
import com.gameshub.service.BookService;
import com.gameshub.service.outern_api.GoogleBookService;
import com.gameshub.validator.BookValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class BookFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookFacade.class);

    private final UserMapper userMapper;
    private final BookMapper bookMapper;
    private final BookService bookService;
    private final GoogleBookService googleBookService;
    private final BookRepository bookRepository;

    private final BookValidator bookValidator;

    public boolean persistAndAddToUserMemorizedBookList(final String googleBookId, final Long userId) throws UserNotFoundException, GoogleBookNotFoundException,
                                                                                                             BookAlreadyMemorizedException {
        GoogleBookDto bookToMemorized = bookMapper.mapToGoogleBookDto(googleBookService.retrieveGoogleBookById(googleBookId));

        Book mappedBook = bookMapper.mapToBook(bookToMemorized);

        if(!bookValidator.validateNewBookAbsence(googleBookId)) {
            bookRepository.save(mappedBook);
            LOGGER.info("New book persisted to database!");
        }
        return bookService.addToUserMemorizedBookList(mappedBook, userId);
    }

    public Set<UserOpenDto> fetchBookUsers(final Long bookId) throws BookNotFoundException {
        return userMapper.mapToUserOpenDtoSet(bookService.getBookUsers(bookId));
    }
}
