package com.gameshub.service.outern_api;

import com.gameshub.client.BookClient;
import com.gameshub.domain.book.googleBook.GoogleBookDto;
import com.gameshub.domain.book.googleBook.GoogleBookListDto;
import com.gameshub.domain.book.googleBook.GoogleBookMainInfoDto;
import com.gameshub.domain.game.Game;
import com.gameshub.exception.GameNotFoundException;
import com.gameshub.exception.GoogleBookNotFoundException;
import com.gameshub.exception.GoogleBooksNotFoundException;
import com.gameshub.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoogleBookServiceTest {

    @InjectMocks
    private GoogleBookService googleBookService;

    @Mock
    private BookClient bookClient;

    @Mock
    private GameRepository gameRepository;

    @Test
    void fetchListOfGoogleBooksRelatedToGameByGameName() throws GoogleBooksNotFoundException {
        //Given
        String gameName = "League of legends";

        GoogleBookMainInfoDto mainVolume1 = GoogleBookMainInfoDto.builder().title("League of legends1").categories(List.of("Games")).language("pl").averageRating(4.0).build();
        GoogleBookMainInfoDto mainVolume2 = GoogleBookMainInfoDto.builder().title("League of legends2").categories(List.of("Games")).language("en").averageRating(5.0).build();
        GoogleBookDto googleBook1 = GoogleBookDto.builder().id("googleId1").selfLink("link1").volumeInfo(mainVolume1).build();
        GoogleBookDto googleBook2 = GoogleBookDto.builder().id("googleId2").selfLink("link2").volumeInfo(mainVolume2).build();
        GoogleBookListDto list1 = GoogleBookListDto.builder().items(Set.of(googleBook1, googleBook2)).build();

        when(bookClient.getBooksList(gameName)).thenReturn(Optional.of(list1));

        //When
        List<GoogleBookDto> resultList = new ArrayList<>(googleBookService.fetchListOfGoogleBooksRelatedToGameByGameName(gameName));

        //Then
        assertEquals(2, resultList.size());
        assertTrue(resultList.containsAll(List.of(googleBook1, googleBook2)));
    }

    @Test
    void fetchListOfGoogleBooksRelatedToGameByGameId() throws GoogleBooksNotFoundException, GameNotFoundException {
        //Given
        Long gameId = 1L;

        Game gameForTest = Game.builder()
                .id(1L)
                .name("League of Legends")
                .build();

        GoogleBookMainInfoDto mainVolume1 = GoogleBookMainInfoDto.builder().title("title1").categories(List.of("Games")).language("pl").averageRating(4.0).build();
        GoogleBookMainInfoDto mainVolume2 = GoogleBookMainInfoDto.builder().title("title2").categories(List.of("Games")).language("en").averageRating(5.0).build();
        GoogleBookDto googleBook1 = GoogleBookDto.builder().id("googleId1").selfLink("link1").volumeInfo(mainVolume1).build();
        GoogleBookDto googleBook2 = GoogleBookDto.builder().id("googleId2").selfLink("link2").volumeInfo(mainVolume2).build();
        GoogleBookListDto list1 = GoogleBookListDto.builder().items(Set.of(googleBook1, googleBook2)).build();

        when(gameRepository.findById(1L)).thenReturn(Optional.ofNullable(gameForTest));
        assert gameForTest != null;
        when(bookClient.getBooksList(gameForTest.getName())).thenReturn(Optional.of(list1));

        //When
        List<GoogleBookDto> resultList = new ArrayList<>(googleBookService.fetchListOfGoogleBooksRelatedToGameByGameId(gameId));


        //Then
        assertEquals(2, resultList.size());
        assertTrue(resultList.containsAll(List.of(googleBook1, googleBook2)));
    }

    @Test
    void retrieveGoogleBookById() throws GoogleBookNotFoundException {
        //Given
        String googleBookId = "googleId1";
        GoogleBookDto googleBook1 = GoogleBookDto.builder().id("googleId1").selfLink("link1").build();
        GoogleBookDto googleBook2 = GoogleBookDto.builder().id("googleId2").selfLink("link2").build();
        GoogleBookListDto list1 = GoogleBookListDto.builder().items(Set.of(googleBook1, googleBook2)).build();


        when(bookClient.getBookById(googleBookId)).thenReturn(Optional.ofNullable(list1));

        //When
        googleBookService.retrieveGoogleBookById(googleBookId);

        //Then
        verify(bookClient, times(1)).getBookById(googleBookId);
    }
}