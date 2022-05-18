package com.gameshub.validator;

import com.gameshub.domain.Profanity;
import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.user.User;
import com.gameshub.exception.GameOpinionUpdateAccessDeniedException;
import com.gameshub.exception.GameOpinionUpdateTimeExpiredException;
import com.gameshub.exception.GameOpinionWithProfanitiesException;
import com.gameshub.exception.UserNotFoundException;
import com.gameshub.repository.ProfanityRepository;
import com.gameshub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class GameOpinionValidator {

        private static final Logger LOGGER = LoggerFactory.getLogger(GameOpinionValidator.class);

        private final ProfanityRepository profanityRepository;
        private final UserRepository userRepository;

        public void validateOpinionForUser(final GameOpinion gameOpinion, final String operationName) throws UserNotFoundException,
                                                                                                             GameOpinionUpdateAccessDeniedException {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentLoggedUserName = authentication.getName();
            User currentLoggedUser = userRepository.findByLoginName(currentLoggedUserName).orElseThrow(UserNotFoundException::new);
            long currentLoggedUserId = currentLoggedUser.getId();

            if (!gameOpinion.getUser().getId().equals(currentLoggedUserId)) {
                LOGGER.error(operationName + "Validation failed! Given game's opinion does not belong to currently logged user!");
                throw new GameOpinionUpdateAccessDeniedException();
            }
        }

        public void validateOpinionForPublicationDate(final GameOpinion gameOpinion, final String operationName) throws GameOpinionUpdateTimeExpiredException {

            LocalDateTime opinionPublicationDate = gameOpinion.getPublicationDate();
            LocalDateTime currentTime = LocalDateTime.now();

            if (opinionPublicationDate.plusMinutes(15).isBefore(currentTime)) {
                LOGGER.error(operationName + "Validation failed! Game's opinion is older then 15 minutes!");
                throw new GameOpinionUpdateTimeExpiredException();
            }
        }

        public void validateOpinionForCurses(final String opinion, final String operationName) throws GameOpinionWithProfanitiesException {
            for (String profanity : mapProfanitiesToString()) {
                if (opinion.contains(profanity)) {
                    LOGGER.error(operationName + "Validation failed! Opinion contains profanities!");
                    throw new GameOpinionWithProfanitiesException();
                }
            }
        }

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
