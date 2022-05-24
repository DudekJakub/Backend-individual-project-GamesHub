package com.gameshub.facade;

import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.game.GameOpinionDto;
import com.gameshub.exception.*;
import com.gameshub.mapper.game.GameOpinionMapper;
import com.gameshub.repository.GameOpinionRepository;
import com.gameshub.service.GameOpinionService;
import com.gameshub.validator.GameOpinionValidator;
import com.gameshub.validator.GameValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.gameshub.facade.OperationName.*;

@Component
@RequiredArgsConstructor
public class GameOpinionFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameOpinionFacade.class);

    private final GameOpinionRepository gameOpinionRepository;
    private final GameOpinionService gameOpinionService;
    private final GameOpinionMapper gameOpinionMapper;

    private final GameValidator gameValidator;
    private final GameOpinionValidator gameOpinionValidator;

    public GameOpinionDto createGameOpinion(final GameOpinionDto gameOpinionDto) throws GameNotFoundException, GameOpinionLengthTooLongException {
        String opinionText = gameOpinionDto.getOpinion();

        gameOpinionValidator.validateOpinionLength(opinionText, CREATE_GAME_OPINION);

        try {
            gameOpinionValidator.validateOpinionForCurses(opinionText, CREATE_GAME_OPINION);
        } catch (GameOpinionWithProfanitiesException e) {
            gameOpinionDto.setOpinion(gameOpinionService.censorProfanities(opinionText));
        }
        GameOpinion newOpinion = gameOpinionService.createGameOpinion(gameOpinionDto);

        return gameOpinionMapper.mapToGameOpinionDto(newOpinion);
    }

    public GameOpinionDto updateGameOpinion(String updatedText, final Long opinionId) throws GameDataUpdateAccessDeniedException, GameOpinionNotFoundException,
                                                                                             GameOpinionLengthTooLongException, GameOpinionUpdateTimeExpiredException,
                                                                                             UserNotFoundException {
        GameOpinion gameOpinionToUpdate = gameOpinionRepository.findById(opinionId).orElseThrow(GameOpinionNotFoundException::new);

        LOGGER.info("Updating opinion with ID: " + opinionId);

        gameOpinionValidator.validateDataBelongingToUser(gameOpinionToUpdate, UPDATE_GAME_OPINION);
        gameOpinionValidator.validateOpinionForPublicationDate(gameOpinionToUpdate, UPDATE_GAME_OPINION);
        gameOpinionValidator.validateOpinionLength(updatedText, UPDATE_GAME_OPINION);

        try {
            gameOpinionValidator.validateOpinionForCurses(updatedText, UPDATE_GAME_OPINION);
        } catch (GameOpinionWithProfanitiesException e) {
            updatedText = gameOpinionService.censorProfanities(updatedText);
        }
        GameOpinion updatedGameOpinion = gameOpinionService.updateGameOpinion(updatedText, gameOpinionToUpdate);

        LOGGER.info("Successfully updated opinion with ID: " + opinionId);

        return gameOpinionMapper.mapToGameOpinionDto(updatedGameOpinion);
    }

    public String deleteGameOpinion(final Long opinionId) throws GameOpinionNotFoundException {
        LOGGER.info("[ADMIN] Deleting game opinion with ID: " + opinionId);
        gameOpinionValidator.validateDatabasePresence(opinionId, DELETE_GAME_OPINION);
        gameOpinionRepository.deleteById(opinionId);
        LOGGER.info("[ADMIN] Successfully deleted opinion with ID: " + opinionId);

        return "Opinion deleted!";
    }

    public List<GameOpinionDto> fetchGameOpinionListForGivenGameId(final Long gameId) throws GameNotFoundException {
        gameValidator.validateDatabasePresence(gameId, FETCH_OPINIONS_FOR_GIVEN_GAME);

        return gameOpinionMapper.mapToListOfGameOpinionDtos(gameOpinionService.getGameOpinionList(gameId));
    }
}
