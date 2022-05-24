package com.gameshub.facade;

public class OperationName {

    //UserFacade
    public static final String SIGN_UP_USER = "[SING UP USER OPERATION] ";
    public static final String RETRIEVE_USER_INFO_FOR_USER_ITSELF_AND_ADMIN = "[RETRIEVE USER RESTRICTED INFO OPERATION] ";

    //GameFacade
    public static final String SUBSCRIBE_GAME = "[SUBSCRIBE GAME OPERATION] ";
    public static final String UNSUBSCRIBE_GAME= "[UNSUBSCRIBE GAME OPERATION] " ;

    //GameOpinionFacade
    public static final String CREATE_GAME_OPINION = "[CREATE NEW GAME OPINION OPERATION] ";
    public static final String UPDATE_GAME_OPINION = "[UPDATE GAME OPINION OPERATION] ";
    public static final String DELETE_GAME_OPINION = "[DELETE GAME OPINION OPERATION] ";
    public static final String FETCH_OPINIONS_FOR_GIVEN_GAME = "[FETCH OPINIONS FOR GAME OPERATION] ";

    //GameRatingFacade
    public static final String CREATE_GAME_RATING = "[CREATE NEW GAME RATING OPERATION] ";
    public static final String UPDATE_GAME_RATING = "[UPDATE GAME RATING OPERATION] " ;
    public static final String DELETE_GAME_RATING = "[DELETE GAME RATING OPERATION] " ;
    public static final String FETCH_RATINGS_FOR_GIVEN_GAME = "[FETCH RATINGS FOR GAME OPERATION] ";
}
