package com.gameshub.controller;

import com.gameshub.domain.statistics.GamesStatisticDto;
import com.gameshub.domain.statistics.StatisticResource;
import com.gameshub.domain.statistics.UsersStatisticDto;
import com.gameshub.exception.GamesStatisticNotFound;
import com.gameshub.exception.StatisticNotFound;
import com.gameshub.exception.UsersStatisticNotFound;
import com.gameshub.facade.StatisticFacade;
import com.gameshub.service.statistic.GamesStatisticService;
import com.gameshub.service.statistic.UsersStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/statistics")
public class StatisticController {

    private final GamesStatisticService gamesStatisticService;
    private final UsersStatisticService usersStatisticService;
    private final StatisticFacade statisticFacade;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/combined")
    public ResponseEntity<List<List<StatisticResource>>> combinedStatistics() {
        return ResponseEntity.ok(statisticFacade.fetchApplicationCombinedStatistics());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/games")
    public ResponseEntity<GamesStatisticDto> newestGamesStatistic() throws StatisticNotFound {
        return ResponseEntity.ok(gamesStatisticService.createAndGetNewStatistic());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{gamesStatsId}")
    public ResponseEntity<GamesStatisticDto> gamesStatistic(@PathVariable Long gamesStatsId) throws GamesStatisticNotFound {
        return ResponseEntity.ok(gamesStatisticService.getStatisticById(gamesStatsId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/games/all")
    public ResponseEntity<List<GamesStatisticDto>> allGamesStatistics() {
        return ResponseEntity.ok(gamesStatisticService.getStatistics());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<UsersStatisticDto> newestUsersStatistic() throws StatisticNotFound {
        return ResponseEntity.ok(usersStatisticService.createAndGetNewStatistic());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{usersStatsId}")
    public ResponseEntity<UsersStatisticDto> usersStatistic(@PathVariable Long usersStatsId) throws UsersStatisticNotFound {
        return ResponseEntity.ok(usersStatisticService.getStatisticById(usersStatsId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users/all")
    public ResponseEntity<List<UsersStatisticDto>> allUsersStatistics() {
        return ResponseEntity.ok(usersStatisticService.getStatistics());
    }
}
