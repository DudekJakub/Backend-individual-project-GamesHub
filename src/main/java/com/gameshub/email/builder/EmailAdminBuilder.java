package com.gameshub.email.builder;

import com.gameshub.configuration.AdminConfiguration;
import com.gameshub.domain.statistics.StatisticResource;
import com.gameshub.domain.user.User;
import com.gameshub.exception.StatisticNotFound;
import com.gameshub.service.statistic.GamesStatisticService;
import com.gameshub.service.statistic.StatisticService;
import com.gameshub.service.statistic.UsersStatisticService;
import org.springframework.stereotype.Component;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmailAdminBuilder extends EmailBuilder {

    private static final String ADMIN_PANEL_PAGE = "http://localhost:8080/v1/statistics/combined";
    private static final String HTTP_ADMIN_STATISTICS = "mail/admin-statistics-email";

    private final GamesStatisticService gamesStatisticService;
    private final UsersStatisticService usersStatisticService;

    public EmailAdminBuilder(final ITemplateEngine iTemplateEngine, final AdminConfiguration adminConfiguration, final GamesStatisticService gamesStatisticService, final UsersStatisticService usersStatisticService) {
        super(iTemplateEngine, adminConfiguration);
        this.gamesStatisticService = gamesStatisticService;
        this.usersStatisticService = usersStatisticService;
    }

    public String buildAdminStatisticsEmail(final String message, final User user) throws StatisticNotFound {
        String previewMessage = "[ADMIN] APPLICATION STATISTICS";
        String buttonName = "ADMIN PANEL";

        List<String> gamesStatistics = prepareStatistics(gamesStatisticService);
        List<String> usersStatistics = prepareStatistics(usersStatisticService);

        Context context = new Context();
        setContext(context, message, user.getFirstname(), ADMIN_PANEL_PAGE);
        context.setVariable("preview_message", previewMessage);
        context.setVariable("button", buttonName);
        context.setVariable("games_statistics", gamesStatistics);
        context.setVariable("users_statistics", usersStatistics);
        context.setVariable("goodbye_message", "Enjoy Your deadlines dear Admin! :)");
        context.setVariable("is_user", false);

        return iTemplateEngine.process(HTTP_ADMIN_STATISTICS, context);
    }

    private List<String> prepareStatistics(StatisticService service) throws StatisticNotFound {
        StatisticResource newestStats = service.createAndGetNewStatistic();
        List<String> statistics = new ArrayList<>();

        int loopCounter = -1;
        for (Field declaredField : newestStats.getClass().getDeclaredFields()) {
            Class<?> fieldType = declaredField.getType();
            if (fieldType.getSimpleName().equals("int") || fieldType.getSimpleName().equals("Long")) {
                loopCounter++;
                statistics.add(declaredField.getName() + ":   " + newestStats.getAllStatValues().get(loopCounter));
            }
        }
        return statistics;
    }
}
