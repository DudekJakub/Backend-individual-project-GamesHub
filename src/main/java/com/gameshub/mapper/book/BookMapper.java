package com.gameshub.mapper.book;

import com.gameshub.domain.book.Book;
import com.gameshub.domain.book.BookDto;
import com.gameshub.domain.book.googleBook.GoogleBookDto;
import com.gameshub.domain.book.googleBook.GoogleBookListDto;
import com.gameshub.domain.user.User;
import com.gameshub.mapper.JsonDateFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookMapper {

    public Book mapToBook(final GoogleBookDto googleBook) {
        return Book.builder()
                .googleId(googleBook.getId())
                .title(googleBook.getVolumeInfo().getTitle())
                .author(googleBook.getVolumeInfo().getAuthors().get(0))
                .category(googleBook.getVolumeInfo().getCategories().get(0))
                .link(googleBook.getSelfLink())
                .publishedDate(JsonDateFormatter.formatStringDateToLocalDate(googleBook.getVolumeInfo().getPublishedDate()))
                .build();
    }

    public BookDto mapToBookDto(final Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .category(book.getCategory())
                .link(book.getLink())
                .publishedDate(book.getPublishedDate())
                .usersIds(book.getUsers().stream().map(User::getId).collect(Collectors.toSet()))
                .build();
    }

    public Set<BookDto> mapToBookListDtos(final Set<Book> books) {
        return books.stream()
                .map(this::mapToBookDto)
                .collect(Collectors.toSet());
    }

    public GoogleBookDto mapToGoogleBookDto(final GoogleBookListDto googleBookListDto) {
        System.out.println("MAPPER " + googleBookListDto.getItems().stream().findFirst().orElseThrow().getVolumeInfo().getTitle());
        return googleBookListDto.getItems().stream().findFirst().orElseThrow();
    }
}
