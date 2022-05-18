package com.gameshub.domain.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameOpinionDto {

    private Long id;
    private String opinion;
    private LocalDateTime publicationDate;
    private Long gameId;
    private Long userId;
}
