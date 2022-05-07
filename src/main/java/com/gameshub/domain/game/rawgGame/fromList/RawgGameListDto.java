package com.gameshub.domain.game.rawgGame.fromList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameshub.domain.game.rawgGame.detailed.RawgGameDetailedDto;
import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RawgGameListDto {

    @JsonProperty(value = "count")
    private Long count;

    @JsonProperty(value = "next")
    private String next;

    @JsonProperty(value = "previous")
    private String previous;

    @JsonProperty(value = "results")
    private List<RawgGameFromListDto> results;
}
