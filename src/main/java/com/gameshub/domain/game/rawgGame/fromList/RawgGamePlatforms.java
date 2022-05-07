package com.gameshub.domain.game.rawgGame.fromList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RawgGamePlatforms {

    @JsonProperty(value = "platform")
    private RawgGamePlatform platform;

    @JsonProperty(value = "released_at")
    private LocalDate releasedAt;

    @Override
    public String toString() {
        return "\n" +platform +
                ", releasedAt=" + releasedAt +
                '}';
    }
}
