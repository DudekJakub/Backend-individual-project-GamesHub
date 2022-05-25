package com.gameshub.service;

import com.gameshub.domain.book.Book;
import com.gameshub.domain.user.User;
import com.gameshub.exception.BookAlreadyMemorizedException;
import com.gameshub.exception.BookNotFoundException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.exception.BookNotMemorizedException;
import com.gameshub.repository.BookRepository;
import com.gameshub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public boolean addToUserMemorizedBookList(final Book book, final Long userId) throws UserNotFoundException, BookAlreadyMemorizedException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if (user.getBooksMemorized().stream().noneMatch(userMemoBook -> userMemoBook.getGoogleId().equals(book.getGoogleId()))) {
            user.getBooksMemorized().add(book);
            userRepository.save(user);
        } else throw new BookAlreadyMemorizedException();

        return true;
    }

    public boolean removeFromUserMemorizedBookList(final Long bookId, final Long userId) throws UserNotFoundException, BookNotFoundException, BookNotMemorizedException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
        Set<Book> userMemorizedBooks = user.getBooksMemorized();

        if (userMemorizedBooks.contains(book)) {
            userMemorizedBooks.remove(book);
            userRepository.save(user);
            return true;
        } else throw new BookNotMemorizedException();
    }

    public Set<User> getBookUsers(final Long bookId) throws BookNotFoundException {
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);

        return book.getUsers();
    }
}
