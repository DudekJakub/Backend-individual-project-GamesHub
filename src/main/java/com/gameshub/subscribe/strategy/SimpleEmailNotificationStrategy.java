package com.gameshub.subscribe.strategy;

import com.gameshub.domain.mail.Mail;
import com.gameshub.email.EmailNotificationService;
import com.gameshub.email.builder.EmailBuilder;
import com.gameshub.subscribe.GameOpinionAddedEvent;
import com.gameshub.subscribe.GameRatingAddedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SimpleEmailNotificationStrategy implements NotificationStrategy {

    private final EmailNotificationService emailNotificationService;

    @Override
    public void sendGameOpinionNotification(final GameOpinionAddedEvent oEvent) {
        List<List<String>> gameAndOpinionInformation = new ArrayList<>();
        List<String> opinionInformation = new ArrayList<>();
        List<String> gameInformation = prepareGameInformationList(oEvent.getGameName(), oEvent.getOpinionsQnt(), oEvent.getRatingsQnt(), oEvent.getAverageCurrentRating());

        opinionInformation.add("NEW OPINION from [" +  oEvent.getUserName() + "]: " + oEvent.getNewOpinion());

        gameAndOpinionInformation.add(gameInformation);
        gameAndOpinionInformation.add(opinionInformation);

        Mail mail = EmailBuilder.createMail(
                oEvent.getUserEmail(),
                "GamesHub - new opinion notification",
                "There is new opinion for game You have subscribed!"
        );
        emailNotificationService.sendGameOpinionNotifyingMail(mail, oEvent.getUserName(), oEvent.getGameOpinion(), gameAndOpinionInformation);
    }

    @Override
    public void sendGameRatingNotification(final GameRatingAddedEvent rEvent) {
        List<List<String>> gameAndRatingInformation = new ArrayList<>();
        List<String> ratingInformation = new ArrayList<>();
        List<String> gameInformation = prepareGameInformationList(rEvent.getGameName(), rEvent.getOpinionsQnt(), rEvent.getRatingsQnt(), rEvent.getAverageCurrentRating());

        ratingInformation.add("NEW RATING from [" + rEvent.getUserName() + "]: " + rEvent.getNewRating() + " at " + rEvent.getPubDate());

        gameAndRatingInformation.add(gameInformation);
        gameAndRatingInformation.add(ratingInformation);

        Mail mail = EmailBuilder.createMail(
                rEvent.getUserEmail(),
                "GamesHub - new rating notification",
                "There are new ratings for game You have subscribed!"
        );
        emailNotificationService.sendGameRatingNotifyingMail(mail, rEvent.getUserName(), rEvent.getGameRating(), gameAndRatingInformation);
    }

    private List<String> prepareGameInformationList(final String gameName, final int opinionsQnt, final int ratingsQnt, final double avgCurrRating) {
        List<String> gameInformation = new ArrayList<>();
        gameInformation.add("GAME NAME: " + gameName);
        gameInformation.add("OPINIONS QUANTITY: " + opinionsQnt);
        gameInformation.add("RATINGS QUANTITY: " + ratingsQnt);

        return gameInformation;
    }
}
