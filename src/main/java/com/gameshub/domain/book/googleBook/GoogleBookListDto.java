package com.gameshub.domain.book.googleBook;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBookListDto {

    private String kind;
    private int totalItems;
    private Set<GoogleBookDto> items;
}
