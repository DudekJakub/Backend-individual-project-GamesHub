package com.gameshub.service;

import com.gameshub.domain.book.Book;
import com.gameshub.domain.user.AppUserNotificationStrategy;
import com.gameshub.domain.user.AppUserRole;
import com.gameshub.domain.user.User;
import com.gameshub.exception.BookAlreadyMemorizedException;
import com.gameshub.exception.BookNotFoundException;
import com.gameshub.exception.BookNotMemorizedException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.repository.BookRepository;
import com.gameshub.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    private User userForTest;
    private Book bookForTest;

    @BeforeEach
    void setSettings() {
        userForTest = User.builder()
                .id(1L)
                .loginName("test")
                .appUserRole(AppUserRole.USER)
                .notificationStrategy(AppUserNotificationStrategy.FULL_EMAIL_NOTIFICATION)
                .email("test_email")
                .password("test_password")
                .firstname("test_firstname")
                .lastname("test_lastname")
                .active(false)
                .verified(true)
                .build();

        bookForTest = Book.builder()
                .id(1L)
                .author("author")
                .googleId("googleId")
                .title("title")
                .build();
    }

    @Test
    void addToUserMemorizedBookList() throws UserNotFoundException, BookAlreadyMemorizedException {
        //Given
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(userForTest));
        when(userRepository.save(userForTest)).thenReturn(userForTest);

        //When
        boolean bookAddedToUserMemoList = bookService.addToUserMemorizedBookList(bookForTest, userId);

        //Then
        assertTrue(bookAddedToUserMemoList);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(userForTest);
    }

    @Test
    void removeFromUserMemorizedBookList() throws UserNotFoundException, BookNotFoundException, BookNotMemorizedException {
        //Given
        Long userId = 1L;
        Long bookId = 1L;

        userForTest.getBooksMemorized().add(bookForTest);

        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(userForTest));
        when(userRepository.save(userForTest)).thenReturn(userForTest);
        when(bookRepository.findById(1L)).thenReturn(Optional.ofNullable(bookForTest));

        //When
        boolean bookRemovedFromUserMemoList = bookService.removeFromUserMemorizedBookList(bookId, userId);
        assertTrue(bookRemovedFromUserMemoList);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(userForTest);
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void getBookUsers() throws BookNotFoundException {
        //Given
        Long bookId = 1L;

        bookForTest.getUsers().add(userForTest);

        when(bookRepository.findById(bookId)).thenReturn(Optional.ofNullable(bookForTest));

        //When
        List<User> resultList = new ArrayList<>(bookService.getBookUsers(bookId));

        //Then
        assertEquals(1, resultList.size());
        assertEquals(1, bookForTest.getUsers().size());
        assertTrue(bookForTest.getUsers().contains(userForTest));
    }
}