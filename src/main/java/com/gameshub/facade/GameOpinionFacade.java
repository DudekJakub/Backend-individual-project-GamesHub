package com.gameshub.facade;

import com.gameshub.domain.game.GameOpinion;
import com.gameshub.domain.game.GameOpinionDto;
import com.gameshub.exception.*;
import com.gameshub.mapper.game.GameOpinionMapper;
import com.gameshub.repository.GameOpinionRepository;
import com.gameshub.service.GameOpinionService;
import com.gameshub.validator.AccountConfirmValidator;
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

    private final GameOpinionService gameOpinionService;
    private final GameOpinionRepository gameOpinionRepository;
    private final GameOpinionMapper gameOpinionMapper;

    private final GameOpinionValidator gameOpinionValidator;
    private final GameValidator gameValidator;
    private final AccountConfirmValidator accountConfirmValidator;

    public GameOpinionDto createGameOpinion(final GameOpinionDto gameOpinionDto) throws UserNotFoundException, UserNotVerifiedException,
                                                                                        GameOpinionWithProfanitiesException, GameNotFoundException {
        Long userId = gameOpinionDto.getUserId();
        String opinionText = gameOpinionDto.getOpinion();

        accountConfirmValidator.validateByUserId(userId, CREATE_GAME_OPINION);
        gameOpinionValidator.validateOpinionForCurses(opinionText, CREATE_GAME_OPINION);

        GameOpinion newOpinion = gameOpinionService.createGameOpinion(gameOpinionDto);

        return gameOpinionMapper.mapToGameOpinionDto(newOpinion);
    }

    public GameOpinionDto updateGameOpinion(final String updatedText, final Long opinionId) throws GameOpinionNotFoundException, GameOpinionUpdateTimeExpiredException,
                                                                                                   GameOpinionWithProfanitiesException, UserNotFoundException,
                                                                                                   GameOpinionUpdateAccessDeniedException {
        GameOpinion GameOpinionToUpdate = gameOpinionRepository.findById(opinionId).orElseThrow(GameOpinionNotFoundException::new);

        LOGGER.info("Updating opinion with ID : " + opinionId);

        gameOpinionValidator.validateOpinionForUser(GameOpinionToUpdate, UPDATE_GAME_OPINION);
        gameOpinionValidator.validateOpinionForPublicationDate(GameOpinionToUpdate, UPDATE_GAME_OPINION);
        gameOpinionValidator.validateOpinionForCurses(updatedText, UPDATE_GAME_OPINION);

        GameOpinion updatedGameOpinion = gameOpinionService.updateGameOpinion(updatedText, GameOpinionToUpdate);

        LOGGER.info("Successfully updated opinion with ID : " + opinionId);

        return gameOpinionMapper.mapToGameOpinionDto(updatedGameOpinion);
    }

    public List<GameOpinionDto> fetchGameOpinionListForGivenGameId(final Long gameId) throws GameOpinionsListNotFoundException, GameNotFoundException {
        if (!gameValidator.checkByIdIfGameExistsInDatabase(gameId, FETCH_OPINIONS_FOR_GIVEN_GAME)) {
            throw new GameOpinionsListNotFoundException();
        }
        return gameOpinionMapper.mapToListOfGameOpinionDtos(gameOpinionService.getGameOpinionListForGivenGameId(gameId));
    }
}
