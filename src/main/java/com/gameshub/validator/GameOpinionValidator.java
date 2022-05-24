package com.gameshub.validator;

import com.gameshub.domain.Profanity;
import com.gameshub.domain.game.GameOpinion;
import com.gameshub.exception.*;
import com.gameshub.repository.GameOpinionRepository;
import com.gameshub.repository.ProfanityRepository;
import com.gameshub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class GameOpinionValidator extends GameDataValidator {

        private static final Logger LOGGER = LoggerFactory.getLogger(GameOpinionValidator.class);

        private static ProfanityRepository profanityRepository;
        private final GameOpinionRepository gameOpinionRepository;

    @Autowired
    public GameOpinionValidator(final UserRepository userRepository, final ProfanityRepository profanityRepository, final GameOpinionRepository gameOpinionRepository) {
        super(userRepository);
        GameOpinionValidator.profanityRepository = profanityRepository;
        this.gameOpinionRepository = gameOpinionRepository;
    }

    public void validateOpinionForPublicationDate(final GameOpinion gameOpinion, final String operationName) throws GameOpinionUpdateTimeExpiredException {
            LocalDateTime opinionPublicationDate = gameOpinion.getPublicationDate();
            LocalDateTime currentTime = LocalDateTime.now();

            if (opinionPublicationDate.plusMinutes(15).isBefore(currentTime)) {
                LOGGER.warn(operationName + "Validation failed! Game's opinion is older then 15 minutes!");
                throw new GameOpinionUpdateTimeExpiredException();
            }
        }

    public void validateOpinionForCurses(final String opinion, final String operationName) throws GameOpinionWithProfanitiesException {
        for (String profanity : mapProfanitiesToString()) {
            if (opinion.contains(profanity)) {
                LOGGER.warn(operationName + "Validation failed! Opinion contains profanities!");
                throw new GameOpinionWithProfanitiesException();
            }
        }
    }

    public void validateOpinionLength(final String opinion, final String operationName) throws GameOpinionLengthTooLongException {
        if (opinion.length() >= 250) {
            LOGGER.warn(operationName + "Validation failed! Opinion is too long!");
            throw new GameOpinionLengthTooLongException();
        }
    }

    public void validateDatabasePresence(final Long gameRatingId, final String operationName) throws GameOpinionNotFoundException {
        boolean existInDatabase = gameOpinionRepository.findById(gameRatingId).isPresent();

        if (!existInDatabase) {
            LOGGER.warn(operationName + "Validation failed! Game opinion not found!");
            throw new GameOpinionNotFoundException();
        }
    }

    @Bean(name = "getProfanities")
    private List<String> mapProfanitiesToString() {
        List<String> mappedProfanities = new ArrayList<>();

        for (Profanity profanity : profanityRepository.findAll()) {
            String mappedProfanity = profanity.getVulgarism();
            String mappedProfanityToLowerCase = mappedProfanity.toLowerCase(Locale.ROOT);
            mappedProfanities.add(mappedProfanity);
            mappedProfanities.add(mappedProfanityToLowerCase);
        }
        return mappedProfanities;
    }
}
