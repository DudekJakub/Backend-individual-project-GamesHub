package com.gameshub.mapper.statistic;

import com.gameshub.domain.statistics.StatisticResource;
import com.gameshub.domain.statistics.UsersStatistic;
import com.gameshub.domain.statistics.UsersStatisticDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersStatisticMapper implements StatisticMapper {

    public UsersStatisticDto mapToUsersStatisticDto(final UsersStatistic statistic) {
        return UsersStatisticDto.builder()
                .statId(statistic.getId())
                .addedDate(statistic.getAddedDate())
                .qnt(statistic.getQnt())
                .withLowActivityStatus(statistic.getWithLowActivityStatus())
                .withMediumActivityStatus(statistic.getWithMediumActivityStatus())
                .withHighActivityStatus(statistic.getWithHighActivityStatus())
                .withTopActivityStatus(statistic.getWithTopActivityStatus())
                .withOpinionsQntLowerThen5(statistic.getWithOpinionsQntLowerThen5())
                .withOpinionsQntBetween5And10(statistic.getWithOpinionsQntBetween5And10())
                .withOpinionsQntHigherThen10(statistic.getWithOpinionsQntHigherThen10())
                .withRatingsQntLowerThen5(statistic.getWithRatingsQntLowerThen5())
                .withRatingsQntBetween5And10(statistic.getWithRatingsQntBetween5And10())
                .withRatingsQntHigherThen10(statistic.getWithRatingsQntHigherThen10())
                .build();
    }

    public List<UsersStatisticDto> mapToUsersStatisticListDtos(final List<UsersStatistic> statistics) {
        return statistics.stream()
                .map(this::mapToUsersStatisticDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StatisticResource> mapToStatisticResourceList(List<?> statistics) {
        return statistics.stream()
                .map(stat -> mapToUsersStatisticDto((UsersStatistic) stat))
                .collect(Collectors.toList());
    }
}
