package com.gameshub.subscribe.strategy;

import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.game.GameRating;
import com.gameshub.domain.mail.Mail;
import com.gameshub.domain.user.User;
import com.gameshub.email.EmailNotificationService;
import com.gameshub.email.builder.EmailBuilder;
import com.gameshub.repository.GameOpinionRepository;
import com.gameshub.repository.GameRatingRepository;
import com.gameshub.subscribe.GameOpinionAddedEvent;
import com.gameshub.subscribe.GameRatingAddedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FullMailNotificationStrategy implements NotificationStrategy {

    private final EmailNotificationService emailNotificationService;
    private final GameOpinionRepository gameOpinionRepository;
    private final GameRatingRepository gameRatingRepository;

    @Override
    public void sendGameOpinionNotification(final GameOpinionAddedEvent oEvent) {
        List<List<String>> gameAndOpinionInformation = new ArrayList<>();
        List<GameOpinion> fourLatestGameOpinions = gameOpinionRepository.retrieveThreeLatestGameOpinionsForGame(oEvent.getGameId());
        List<String> dataForEmail = prepareDataFromLatestGameOpinions(fourLatestGameOpinions);

        List<String> opinionInformation = new ArrayList<>();
        opinionInformation.add("NEW OPINION from [" + oEvent.getUserName() + "]: " + oEvent.getNewOpinion());
        opinionInformation.add("at " + oEvent.getPubDate());
        opinionInformation.add("LATEST OPINIONS: ");
        opinionInformation.addAll(dataForEmail);

        gameAndOpinionInformation.add(prepareGameInformationList(oEvent.getGameName(),  oEvent.getOpinionsQnt(), oEvent.getRatingsQnt(),
                                                                 oEvent.getOpinionsPerDay(), oEvent.getRatingsPerDay(), oEvent.getAverageCurrentRating()));
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
        List<GameRating> sixLatestGameRatings = gameRatingRepository.retrieveSixLatestGameRatingsForGame(rEvent.getGameId());
        List<String> dataForEmail = prepareDataFromLatestGameRatings(sixLatestGameRatings);

        List<String> ratingInformation = new ArrayList<>();
        ratingInformation.add("NEW RATING from [" + rEvent.getUserName() + "]: " + rEvent.getNewRating() + " at " + rEvent.getPubDate());
        ratingInformation.add("LATEST RATINGS: ");
        ratingInformation.addAll(dataForEmail);

        gameAndRatingInformation.add(prepareGameInformationList(rEvent.getGameName(), rEvent.getOpinionsQnt(), rEvent.getRatingsQnt(),
                                                                rEvent.getOpinionsPerDay(), rEvent.getRatingsPerDay(), rEvent.getAverageCurrentRating()));
        gameAndRatingInformation.add(ratingInformation);

        Mail mail = EmailBuilder.createMail(
                rEvent.getUserEmail(),
                "GamesHub - new rating notification",
                "There are new ratings for game You have subscribed!"
        );
        emailNotificationService.sendGameRatingNotifyingMail(mail, rEvent.getUserName(), rEvent.getGameRating(), gameAndRatingInformation);
    }

    private List<String> prepareGameInformationList(final String gameName, final int opinionsQnt, final int ratingsQnt,
                                                    final double opinionsPerDay, final double ratingsPerDay, final double avgCurrRating) {
        List<String> gameInformation = new ArrayList<>();
        gameInformation.add("GAME NAME: " + gameName);
        gameInformation.add("OPINIONS QUANTITY: " + opinionsQnt);
        gameInformation.add("RATINGS QUANTITY: " + ratingsQnt);
        gameInformation.add("AVG. OPINIONS PER DAY: " + opinionsPerDay);
        gameInformation.add("AVG. RATINGS PER DAY: " + ratingsPerDay);
        gameInformation.add("AVG. CURRENT RATING: " + avgCurrRating);

        return gameInformation;
    }

    private List<String> prepareDataFromLatestGameOpinions(final List<GameOpinion> fourLatestGameOpinions) {
        String userFirstname;
        String userOpinion;
        LocalDateTime userOpinionPubDate;
        User userWithOpinion;

        fourLatestGameOpinions.remove(0);

        List<String> resultList = new ArrayList<>();

        for (GameOpinion opinion : fourLatestGameOpinions) {
            userWithOpinion = opinion.getUser();
            userFirstname = userWithOpinion.getFirstname();
            userOpinion = opinion.getOpinion();
            userOpinionPubDate = opinion.getPublicationDate();

            resultList.add("[" + userFirstname + "] wrote opinion:");
            resultList.add("\"" + userOpinion + "\"");
            resultList.add("[PUB. DATE] " + userOpinionPubDate);
        }
        return resultList;
    }

    private List<String> prepareDataFromLatestGameRatings(final List<GameRating> sixLatestGameRatings) {
        String userFirstname;
        double userRating;
        LocalDateTime userRatingPubDate;
        User userWithRating;

        sixLatestGameRatings.remove(0);

        List<String> resultList = new ArrayList<>();

        for (GameRating rating : sixLatestGameRatings) {
            userWithRating = rating.getUser();
            userFirstname = userWithRating.getFirstname();
            userRating = rating.getRating();
            userRatingPubDate = rating.getPublicationDate();

            resultList.add("[" + userFirstname + "] added rating: " + userRating + " at " + userRatingPubDate);
        }
        return resultList;
    }
}
