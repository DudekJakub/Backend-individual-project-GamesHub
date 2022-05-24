package com.gameshub.domain.book.googleBook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBookMainInfoDto {

    private String title;
    private List<String> authors;
    private List<String> categories;
    private String publisher;
    private String description;
    private int pageCount;
    private double averageRating;
    private int ratingsCount;
    private String language;
    private String publishedDate;
}
