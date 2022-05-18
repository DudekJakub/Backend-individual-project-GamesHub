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
public class RawgGameDetailedDto {

    private Long id;
    private String name;
    private String description;
    private double metacritic;
    private LocalDate released;
    private boolean tba;

    @JsonProperty(value = "website")
    private String officialWebsiteURL;

    @Override
    public String toString() {
        return "\n\nRawgGameDetailedDto{" +
                "\nid=" + id +
                "\nname='" + name + '\'' +
                "\ndescription='" + description + '\'' +
                "\nmetacritic=" + metacritic +
                "\nreleased=" + released +
                "\ntba=" + tba +
                "\nofficialWebsiteURL='" + officialWebsiteURL + '\'' +
                '}';
    }
}
