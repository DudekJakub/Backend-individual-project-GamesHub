package com.gameshub.domain.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;
    private String googleId;
    private String title;
    private String author;
    private String category;
    private String link;
    private LocalDate publishedDate;
    private Set<Long> usersIds;
}
