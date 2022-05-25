package com.gameshub.mapper.book;

import com.gameshub.domain.book.Book;
import com.gameshub.domain.book.BookDto;
import com.gameshub.domain.book.googleBook.GoogleBookDto;
import com.gameshub.domain.book.googleBook.GoogleBookListDto;
import com.gameshub.domain.book.googleBook.GoogleBookMainInfoDto;
import com.gameshub.domain.book.googleBook.GoogleBookSaleInfo;
import com.gameshub.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {

    private final BookMapper mapper = new BookMapper();

    private Book book;
    private GoogleBookDto bookDto;

    @BeforeEach
    void setSettings() {
        book = Book.builder()
                .id(1L)
                .author("author")
                .title("title")
                .category("category")
                .link("link")
                .build();

        bookDto = GoogleBookDto.builder()
                .id("id")
                .selfLink("selfLink")
                .saleInfo(GoogleBookSaleInfo.builder().country("POLAND").build())
                .volumeInfo(GoogleBookMainInfoDto.builder().title("title").authors(List.of("author")).categories(List.of("category")).publishedDate("2000-01-03").build())
                .build();
    }

    @Test
    void mapToBook() {
        //Given
            //setSettings()

        //When
        Book mappedBook = mapper.mapToBook(bookDto);

        //Then
        assertEquals(book.getAuthor(), mappedBook.getAuthor());
        assertEquals(book.getTitle(), mappedBook.getTitle());
        assertEquals(book.getCategory(), mappedBook.getCategory());
    }

    @Test
    void mapToBookDto() {
        //Given
            //setSettings()

        //When
        BookDto mappedBook = mapper.mapToBookDto(book);

        //Then
        assertEquals(book.getAuthor(), mappedBook.getAuthor());
        assertEquals(book.getTitle(), mappedBook.getTitle());
        assertEquals(book.getCategory(), mappedBook.getCategory());
    }

    @Test
    void mapToBookListDtos() {
        //Given
            //setSettings()

        Set<Book> books = Set.of(book);

        //When
        Set<BookDto> bookDtos = mapper.mapToBookListDtos(books);

        //Then
        List<Book> booksList = new ArrayList<>(books);
        List<BookDto> booksDtoList = new ArrayList<>(bookDtos);

        assertEquals(booksList.get(0).getTitle(), booksDtoList.get(0).getTitle());
        assertEquals(booksList.get(0).getAuthor(), booksDtoList.get(0).getAuthor());
        assertEquals(booksList.get(0).getCategory(), booksDtoList.get(0).getCategory());
    }

    @Test
    void mapToGoogleBookDto() {
        //Given
        GoogleBookListDto googleBookListDto = GoogleBookListDto.builder().items(Set.of(bookDto)).totalItems(1).build();

        //When
        var resultGoogleDto = mapper.mapToGoogleBookDto(googleBookListDto);

        //Then
        var bookDtoSet = googleBookListDto.getItems().stream().findAny().orElseThrow();

        assertEquals(resultGoogleDto.getId(), bookDtoSet.getId());
        assertEquals(resultGoogleDto.getSaleInfo(), bookDtoSet.getSaleInfo());
        assertEquals(resultGoogleDto.getSelfLink(), bookDtoSet.getSelfLink());
    }
}