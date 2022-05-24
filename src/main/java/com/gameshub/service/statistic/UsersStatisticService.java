package com.gameshub.service.statistic;

import com.gameshub.domain.statistics.UsersStatistic;
import com.gameshub.domain.statistics.UsersStatisticDto;
import com.gameshub.exception.StatisticNotFound;
import com.gameshub.exception.UsersStatisticNotFound;
import com.gameshub.mapper.statistic.UsersStatisticMapper;
import com.gameshub.repository.UsersStatisticRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersStatisticService implements StatisticService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersStatisticService.class);

    private final UsersStatisticRepository usersStatRepository;
    private final UsersStatisticMapper usersStatMapper;

    public UsersStatisticDto createAndGetNewStatistic() throws StatisticNotFound {
        try {
            LOGGER.info("Creating new users statistic...");
            usersStatRepository.createNewUsersStats();
            LOGGER.info("Statistic created successfully!");
        } catch (Exception e) {
            LOGGER.warn("Statistic created failed! Exception occurred! " + e.getMessage());
        }
        UsersStatistic statistic = usersStatRepository.retrieveTheNewestStats().orElseThrow(StatisticNotFound::new);
        return usersStatMapper.mapToUsersStatisticDto(statistic);
    }

    public UsersStatisticDto getStatisticById(final Long usersStatsId) throws UsersStatisticNotFound {
        UsersStatistic usersStatistic = usersStatRepository.findById(usersStatsId).orElseThrow(UsersStatisticNotFound::new);
        return usersStatMapper.mapToUsersStatisticDto(usersStatistic);
    }

    public List<UsersStatisticDto> getStatistics() {
        List<UsersStatistic> statistics = usersStatRepository.findAll();
        return usersStatMapper.mapToUsersStatisticListDtos(statistics);
    }
}
