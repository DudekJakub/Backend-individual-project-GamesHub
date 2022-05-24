package com.gameshub.domain.game.rawgGame;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RawgGamePlatformsDto {

    @JsonProperty(value = "platform")
    private RawgGamePlatformDto platform;

    @JsonProperty(value = "released_at")
    private LocalDate releasedAt;
}
