package com.gameshub.email.builder;

import com.gameshub.configuration.AdminConfiguration;
import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.game.GameRating;
import com.gameshub.domain.mail.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmailNotificationBuilder extends EmailBuilder {

    private static final String HTTP_GAME_OPINIONS_NOTIFYING_MAIL = "mail/game-opinions-notifying-email";
    private static final String HTTP_GAME_RATINGS_NOTIFYING_MAIL = "mail/game-ratings-notifying-email";

    public EmailNotificationBuilder(ITemplateEngine iTemplateEngine, AdminConfiguration adminConfiguration) {
        super(iTemplateEngine, adminConfiguration);
    }

    public String buildGameOpinionNotifyingEmail(final String message, final String userName, final GameOpinion gameOpinion,
                                                 final List<List<String>> gameAndOpinionInformation) {
        String gamePageURL = "http://localhost:8080/v1/opinions/" + gameOpinion.getGame().getId();

        Context context = new Context();
        setContext(context, message, userName, gamePageURL);
        context.setVariable("preview_message", "New opinion for game " + gameOpinion.getGameName() + "!");
        context.setVariable("button", "CHECK OPINIONS");
        context.setVariable("game_information", gameAndOpinionInformation.get(0));
        context.setVariable("opinion_information", gameAndOpinionInformation.get(1));

        return iTemplateEngine.process(HTTP_GAME_OPINIONS_NOTIFYING_MAIL, context);
    }

    public String buildGameRatingNotifyingEmail(final String message, final String userName, final GameRating gameRating,
                                                final List<List<String>> gameAndRatingInformation) {
        String gamePageURL = "http://localhost:8080/v1/ratings/" + gameRating.getGame().getId();

        Context context = new Context();
        setContext(context, message, userName, gamePageURL);
        context.setVariable("preview_message", "New ratings for game " + gameRating.getGameName() + "!");
        context.setVariable("button", "CHECK RATINGS");
        context.setVariable("game_information", gameAndRatingInformation.get(0));
        context.setVariable("rating_information", gameAndRatingInformation.get(1));

        return iTemplateEngine.process(HTTP_GAME_RATINGS_NOTIFYING_MAIL, context);
    }
}
