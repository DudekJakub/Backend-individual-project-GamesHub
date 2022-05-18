package com.gameshub.facade;

public class OperationName {

    //UserFacade
    public static final String SIGN_UP_USER = "[SING UP USER OPERATION] ";
    public static final String SWITCH_USER_NOTIFICATION_STRATEGY = "[SWITCH USER NOTIFICATION STRATEGY OPERATION] ";
    public static final String FETCH_USERS_LIST_FOR_REGULAR_USER = "[FETCH USERS LIST FOR REGULAR USER] ";
    public static final String FETCH_USERS_LIST_FOR_ADMIN = "[FETCH USERS LIST FOR ADMIN] ";

    //GameOpinionFacade
    public static final String CREATE_GAME_OPINION = "[CREATE NEW GAME OPINION OPERATION] ";
    public static final String UPDATE_GAME_OPINION = "[UPDATE GAME OPINION OPERATION] ";
    public static final String FETCH_OPINIONS_FOR_GIVEN_GAME = "[FETCH OPINIONS FOR GAME OPERATION] ";

    //GameFacade
    public static final String SUBSCRIBE_GAME = "[SUBSCRIBE GAME OPERATION] ";
    public static final String UNSUBSCRIBE_GAME= "[UNSUBSCRIBE GAME OPERATION] " ;
    public static final String MARK_GAME_AS_OWNED = "[MARK GAME AS OWNED OPERATION] ";
    public static final String MARK_GAME_AS_WANTED_TO_OWN = "[MARK GAME AS WANTED TO OWN OPERATION] ";
    public static final String FETCH_GAME_OBSERVERS = "[FETCH GAME OBSERVERS OPERATION] ";
    public static final String FETCH_GAMES_USER_OWNS = "[FETCH GAMES USER OWNS OPERATION] ";
    public static final String FETCH_GAMES_USER_WANTS = "[FETCH GAMES USER WANTS TO OWN OPERATION] ";
}
