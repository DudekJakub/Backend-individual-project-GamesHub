create table if not exists application_notifications
(
    id                  bigint       not null
        primary key,
    notification_date   datetime     null,
    referenced_event_id bigint       null,
    game_id             bigint       null,
    system_status       varchar(255) null,
    title               varchar(255) null
)
    engine = MyISAM;

create table if not exists books
(
    id             bigint       not null
        primary key,
    author         varchar(255) null,
    google_id      varchar(255) null,
    link           varchar(255) null,
    published_date date         null,
    title          varchar(255) null,
    category       varchar(255) null
)
    engine = MyISAM;

create table if not exists game_ratings
(
    id               bigint       not null
        primary key,
    game_name        varchar(255) not null,
    publication_date datetime     not null,
    rating           double       null,
    game_id          bigint       null,
    user_id          bigint       null
)
    engine = MyISAM;

create index FKgi82hqle7glirn5uo8h8pgeci
    on game_ratings (user_id);

create index FKldl045nx5l0o7114fdcm7kfy8
    on game_ratings (game_id);

create definer = root@localhost trigger GAME_TABLE_AVERAGE_RATING_DELETE_UPDATE
    after delete
    on game_ratings
    for each row
BEGIN

    DECLARE RESULT DOUBLE;
    SELECT AVG(RATING) FROM GAMES_AND_RATINGS WHERE ID = old.GAME_ID INTO RESULT;

    UPDATE GAMES SET GAMES.AVERAGE_RATING = RESULT WHERE ID = old.GAME_ID;
end;

create definer = root@localhost trigger GAME_TABLE_AVERAGE_RATING_INSERT_UPDATE
    after insert
    on game_ratings
    for each row
BEGIN

    DECLARE RESULT DOUBLE;
    SELECT AVG(RATING) FROM GAMES_AND_RATINGS WHERE ID = new.GAME_ID INTO RESULT;

    UPDATE GAMES SET GAMES.AVERAGE_RATING = RESULT WHERE ID = new.GAME_ID;
end;

create definer = root@localhost trigger GAME_TABLE_RATINGS_PER_DAY_DELETE_UPDATE
    after delete
    on game_ratings
    for each row
BEGIN
    UPDATE GAMES SET GAMES.RATINGS_PER_DAY = GameRatingsPerDay(old.GAME_ID) WHERE ID = old.GAME_ID;
end;

create definer = root@localhost trigger GAME_TABLE_RATINGS_PER_DAY_INSERT_UPDATE
    after insert
    on game_ratings
    for each row
BEGIN
    UPDATE GAMES SET GAMES.RATINGS_PER_DAY = GameRatingsPerDay(new.GAME_ID) WHERE ID = new.GAME_ID;
end;

create definer = root@localhost trigger GAME_TABLE_RATINGS_QNT_DELETE_UPDATE
    after delete
    on game_ratings
    for each row
BEGIN
    UPDATE GAMES SET GAMES.RATINGS_QUANTITY = GAMES.RATINGS_QUANTITY - 1 WHERE ID = old.GAME_ID;
end;

create definer = root@localhost trigger GAME_TABLE_RATINGS_QNT_INSERT_UPDATE
    after insert
    on game_ratings
    for each row
BEGIN
    UPDATE GAMES SET GAMES.RATINGS_QUANTITY = GAMES.RATINGS_QUANTITY + 1 WHERE ID = new.GAME_ID;
end;

create definer = root@localhost trigger USER_TABLE_RATINGS_QNT_DELETE_UPDATE
    after delete
    on game_ratings
    for each row
BEGIN
    UPDATE USERS SET USERS.RATINGS_QNT = USERS.RATINGS_QNT - 1 WHERE ID = old.USER_ID;
end;

create definer = root@localhost trigger USER_TABLE_RATINGS_QNT_INSERT_UPDATE
    after insert
    on game_ratings
    for each row
BEGIN
    UPDATE USERS SET USERS.RATINGS_QNT = USERS.RATINGS_QNT + 1 WHERE ID = new.USER_ID;
end;

create table if not exists games
(
    ID                bigint unsigned auto_increment
        primary key,
    NAME              varchar(100)              not null,
    AVERAGE_RATING    double      default 0     null,
    RATINGS_QUANTITY  int         default 0     null,
    RATINGS_PER_DAY   double      default 0     null,
    OPINIONS_QUANTITY int         default 0     null,
    OPINIONS_PER_DAY  double      default 0     null,
    POPULARITY_STATUS varchar(30) default 'LOW' null,
    constraint ID
        unique (ID)
);

create table if not exists games_have_observers
(
    game_id bigint not null,
    user_id bigint not null,
    primary key (game_id, user_id)
)
    engine = MyISAM;

create index FKh94l827tuxfdc6be3qg50f480
    on games_have_observers (user_id);

create table if not exists games_statistics
(
    ID                                bigint unsigned auto_increment
        primary key,
    ADDED_DATE                        datetime default CURRENT_TIMESTAMP null,
    QNT                               int      default 0                 null,
    OPINIONS_QNT                      int      default 0                 null,
    WITH_LOW_POP_STATUS               int      default 0                 null,
    WITH_MEDIUM_POP_STATUS            int      default 0                 null,
    WITH_HIGH_POP_STATUS              int      default 0                 null,
    WITH_VERY_HIGH_POP_STATUS         int      default 0                 null,
    WITH_ON_THE_TOP_POP_STATUS        int      default 0                 null,
    WITH_AVG_RATING_LOWER_THEN_FIVE   int      default 0                 null,
    WITH_AVG_RATING_BETWEEN_5_AND_7_5 int      default 0                 null,
    WITH_AVG_RATING_BETWEEN_7_5_AND_9 int      default 0                 null,
    WITH_AVG_RATING_HIGHER_EQUALS_9   int      default 0                 null,
    constraint ID
        unique (ID)
);

create table if not exists hibernate_sequence
(
    next_val bigint null
)
    engine = MyISAM;

create table if not exists profanities
(
    ID        bigint unsigned auto_increment
        primary key,
    VULGARISM varchar(15) not null,
    constraint ID
        unique (ID)
);

create table if not exists users
(
    ID                         bigint unsigned auto_increment
        primary key,
    LOGIN_NAME                 varchar(30)                 not null,
    ROLES                      varchar(10)                 not null,
    REGISTERED_DATE            datetime    default (now()) not null,
    EMAIL                      varchar(50)                 not null,
    FIRSTNAME                  varchar(30)                 not null,
    LASTNAME                   varchar(30)                 not null,
    PASSWORD                   varchar(255)                null,
    ACTIVE                     tinyint(1)                  not null,
    VERIFIED                   tinyint(1)                  not null,
    user_notification_strategy varchar(255)                null,
    OPINIONS_QNT               int         default 0       null,
    OPINIONS_PER_DAY           double      default 0       null,
    RATINGS_QNT                int         default 0       null,
    RATINGS_PER_DAY            double      default 0       null,
    ACTIVITY_STATUS            varchar(20) default 'LOW'   null,
    constraint ID
        unique (ID),
    constraint LOGIN_NAME
        unique (LOGIN_NAME)
);

create table if not exists game_opinions
(
    ID               bigint unsigned auto_increment
        primary key,
    GAME_ID          bigint unsigned          not null,
    USER_ID          bigint unsigned          not null,
    GAME_NAME        varchar(100)             not null,
    OPINION          varchar(500)             not null,
    PUBLICATION_DATE datetime default (now()) not null,
    user_login       varchar(255)             null,
    constraint ID
        unique (ID),
    constraint game_opinions_ibfk_1
        foreign key (USER_ID) references users (ID),
    constraint game_opinions_ibfk_2
        foreign key (GAME_ID) references games (ID)
);

create index GAME_ID
    on game_opinions (GAME_ID);

create index USER_ID
    on game_opinions (USER_ID);

create definer = root@localhost trigger GAME_TABLE_OPINIONS_PER_DAY_INSERT_UPDATE
    after insert
    on game_opinions
    for each row
BEGIN
    UPDATE GAMES SET GAMES.OPINIONS_PER_DAY = GameOpinionsPerDay(new.GAME_ID) WHERE ID = new.GAME_ID;
end;

create definer = root@localhost trigger GAME_TABLE_OPINIONS_QNT_DELETE_UPDATE
    after delete
    on game_opinions
    for each row
BEGIN
    UPDATE GAMES SET GAMES.OPINIONS_QUANTITY = GAMES.OPINIONS_QUANTITY - 1 WHERE ID = old.GAME_ID;
end;

create definer = root@localhost trigger GAME_TABLE_OPINIONS_QNT_INSERT_UPDATE
    after insert
    on game_opinions
    for each row
BEGIN
    UPDATE GAMES SET GAMES.OPINIONS_QUANTITY = GAMES.OPINIONS_QUANTITY + 1 WHERE ID = new.GAME_ID;
end;

create definer = root@localhost trigger GAME_TABLE_OPINION_PER_DAY_DELETE_UPDATE
    after delete
    on game_opinions
    for each row
BEGIN
    UPDATE GAMES SET GAMES.OPINIONS_PER_DAY = GameOpinionsPerDay(old.GAME_ID) WHERE ID = old.GAME_ID;
end;

create definer = root@localhost trigger USER_TABLE_OPINIONS_QNT_DELETE_UPDATE
    after delete
    on game_opinions
    for each row
BEGIN
    UPDATE USERS SET USERS.OPINIONS_QNT = USERS.OPINIONS_QNT - 1 WHERE ID = old.USER_ID;
end;

create definer = root@localhost trigger USER_TABLE_OPINIONS_QNT_INSERT_UPDATE
    after insert
    on game_opinions
    for each row
BEGIN
    UPDATE USERS SET USERS.OPINIONS_QNT = USERS.OPINIONS_QNT + 1 WHERE ID = new.USER_ID;
end;

create table if not exists users_have_app_notifications
(
    notification_id bigint not null,
    user_id         bigint not null,
    primary key (notification_id, user_id)
)
    engine = MyISAM;

create index FKcu0y1qsl02algcmvgftvs6q99
    on users_have_app_notifications (user_id);

create table if not exists users_have_games
(
    user_id bigint not null,
    game_id bigint not null,
    primary key (user_id, game_id)
)
    engine = MyISAM;

create index FK1rv7j2h8uweeavb94y0wawx2v
    on users_have_games (game_id);

create table if not exists users_memorized_books
(
    user_id bigint not null,
    book_id bigint not null,
    primary key (user_id, book_id)
)
    engine = MyISAM;

create index FKo436f70kn6y77og059652285g
    on users_memorized_books (book_id);

create table if not exists users_statistics
(
    ID                                 bigint unsigned auto_increment
        primary key,
    ADDED_DATE                         datetime default CURRENT_TIMESTAMP null,
    QNT                                int      default 0                 null,
    CONFIRMED                          int      default 0                 null,
    REGULAR_USERS                      int      default 0                 null,
    ADMINS                             int      default 0                 null,
    WITH_LOW_ACTIVITY_STATUS           int      default 0                 null,
    WITH_MEDIUM_ACTIVITY_STATUS        int      default 0                 null,
    WITH_HIGH_ACTIVITY_STATUS          int      default 0                 null,
    WITH_TOP_ACTIVITY_STATUS           int      default 0                 null,
    WITH_OPINIONS_QNT_LOWER_THEN_5     int      default 0                 null,
    WITH_OPINIONS_QNT_BETWEEN_5_AND_10 int      default 0                 null,
    WITH_OPINIONS_QNT_HIGHER_THEN_10   int      default 0                 null,
    WITH_RATINGS_QNT_LOWER_THEN_5      int      default 0                 null,
    WITH_RATINGS_QNT_BETWEEN_5_AND_10  int      default 0                 null,
    WITH_RATINGS_QTN_HIGHER_THEN_10    int      default 0                 null,
    constraint ID
        unique (ID)
);

create table if not exists users_want_to_have_games
(
    user_id bigint not null,
    game_id bigint not null,
    primary key (user_id, game_id)
)
    engine = MyISAM;

create index FK8iumgpvsyagxfsgraugv4i2c2
    on users_want_to_have_games (game_id);

create or replace definer = root@localhost view games_and_opinions as
select `gm`.`ID` AS `ID`, `gm`.`NAME` AS `NAME`, `go`.`OPINION` AS `OPINION`
from `gameshub`.`games` `gm`
         join `gameshub`.`game_opinions` `go`
where (`gm`.`ID` = `go`.`GAME_ID`);

create or replace definer = root@localhost view games_and_ratings as
select `gm`.`ID` AS `ID`, `gm`.`NAME` AS `NAME`, `gr`.`rating` AS `RATING`
from `gameshub`.`games` `gm`
         join `gameshub`.`game_ratings` `gr`
where (`gm`.`ID` = `gr`.`game_id`);

create or replace definer = root@localhost view opinions_qnt_and_datediff_in_days as
select count(0)                                                            AS `OPINIONS_QNT`,
       ((to_days(max(`gameshub`.`game_opinions`.`PUBLICATION_DATE`)) -
         to_days(min(`gameshub`.`game_opinions`.`PUBLICATION_DATE`))) + 1) AS `DAYS`
from `gameshub`.`game_opinions`
where (`gameshub`.`game_opinions`.`GAME_ID` = 32);

create
    definer = root@localhost procedure CreateNewGamesStats()
BEGIN
    DECLARE QNT, OPINIONS_QNT, LOW_POP, MEDIUM_POP, HIGH_POP,
        VERY_HIGH_POP, ON_TOP_POP, AVG_RATING_LOWER_THEN_5, AVG_RATING_BETWEEN_5_AND_7_5,
        AVG_RATING_BETWEEN_7_5_AND_9, AVG_RATING_HIGHER_EQUALS_9 INT;

    SET QNT = (SELECT COUNT(*) FROM GAMES);
    SET OPINIONS_QNT = (SELECT COUNT(*) FROM GAME_OPINIONS GROUP BY GAME_ID);
    SET LOW_POP = (SELECT COUNT(*) FROM GAMES G WHERE G.POPULARITY_STATUS = 'LOW' GROUP BY G.POPULARITY_STATUS);
    SET MEDIUM_POP = (SELECT COUNT(*) FROM GAMES G WHERE G.POPULARITY_STATUS = 'MEDIUM' GROUP BY G.POPULARITY_STATUS);
    SET HIGH_POP = (SELECT COUNT(*) FROM GAMES G WHERE G.POPULARITY_STATUS = 'HIGH' GROUP BY G.POPULARITY_STATUS);
    SET VERY_HIGH_POP = (SELECT COUNT(*) FROM GAMES G WHERE G.POPULARITY_STATUS = 'VERY HIGH' GROUP BY G.POPULARITY_STATUS);
    SET ON_TOP_POP = (SELECT COUNT(*) FROM GAMES G WHERE G.POPULARITY_STATUS = 'ON THE TOP' GROUP BY G.POPULARITY_STATUS);
    SET AVG_RATING_LOWER_THEN_5 = (SELECT COUNT(*) FROM GAMES G WHERE G.AVERAGE_RATING < 5.0 GROUP BY G.AVERAGE_RATING);
    SET AVG_RATING_BETWEEN_5_AND_7_5 = (SELECT COUNT(*) FROM GAMES G WHERE G.AVERAGE_RATING > 5.0 AND G.AVERAGE_RATING <= 7.5 GROUP BY G.AVERAGE_RATING);
    SET AVG_RATING_BETWEEN_7_5_AND_9 = (SELECT COUNT(*) FROM GAMES G WHERE G.AVERAGE_RATING > 7.5 AND G.AVERAGE_RATING < 9 GROUP BY G.AVERAGE_RATING);
    SET AVG_RATING_HIGHER_EQUALS_9 = (SELECT COUNT(*) FROM GAMES G WHERE G.AVERAGE_RATING >= 9.0 GROUP BY G.AVERAGE_RATING);

    INSERT INTO gameshub.GAMES_STATISTICS VALUES (NULL, CURRENT_TIME, IFNULL(QNT, 0), IFNULL(OPINIONS_QNT, 0), IFNULL(LOW_POP, 0),
                                                  IFNULL(MEDIUM_POP, 0), IFNULL(HIGH_POP, 0), IFNULL(VERY_HIGH_POP, 0),
                                                  IFNULL(ON_TOP_POP, 0), IFNULL(AVG_RATING_LOWER_THEN_5, 0),
                                                  IFNULL(AVG_RATING_BETWEEN_5_AND_7_5, 0), IFNULL(AVG_RATING_BETWEEN_7_5_AND_9, 0),
                                                  IFNULL(AVG_RATING_HIGHER_EQUALS_9, 0));
    COMMIT ;
end;

create
    definer = root@localhost procedure CreateNewUsersStats()
BEGIN
    DECLARE QNT, CONFIRMED, REGULAR_USERS, ADMINS, L_ACT, M_ACT, H_ACT, T_AVT,
        OPINIONS_LOWER_5, OPINIONS_BETWEEN_5_10, OPINIONS_HIGHER_10, RATINGS_LOWER_5,
        RATINGS_BETWEEN_5_10, RATINGS_HIGHER_THEN_10 INT;

    SET QNT = (SELECT COUNT(*) FROM USERS);
    SET CONFIRMED = (SELECT COUNT(*) FROM USERS U WHERE U.VERIFIED = TRUE);
    SET REGULAR_USERS = (SELECT COUNT(*) FROM USERS U WHERE U.ROLES = 'USER');
    SET ADMINS = (SELECT COUNT(*) FROM USERS U WHERE U.ROLES = 'ADMIN');
    SET L_ACT = (SELECT COUNT(*) FROM USERS U WHERE U.ACTIVITY_STATUS = 'LOW');
    SET M_ACT = (SELECT COUNT(*) FROM USERS U WHERE U.ACTIVITY_STATUS = 'MEDIUM');
    SET H_ACT = (SELECT COUNT(*) FROM USERS U WHERE U.ACTIVITY_STATUS = 'HIGH');
    SET T_AVT = (SELECT COUNT(*) FROM USERS U WHERE U.ACTIVITY_STATUS = 'TOP');
    SET OPINIONS_LOWER_5 = (SELECT COUNT(*) FROM USERS U WHERE U.OPINIONS_QNT < 5);
    SET OPINIONS_BETWEEN_5_10 = (SELECT COUNT(*) FROM USERS U WHERE U.OPINIONS_QNT >= 5 AND U.OPINIONS_QNT <= 10);
    SET OPINIONS_HIGHER_10 = (SELECT COUNT(*) FROM USERS U WHERE U.OPINIONS_QNT > 10);
    SET RATINGS_LOWER_5 = (SELECT COUNT(*) FROM USERS U WHERE U.RATINGS_QNT < 5);
    SET RATINGS_BETWEEN_5_10 = (SELECT COUNT(*) FROM USERS U WHERE U.RATINGS_QNT >= 5 AND U.RATINGS_QNT <= 10);
    SET RATINGS_HIGHER_THEN_10 = (SELECT COUNT(*) FROM USERS U WHERE U.RATINGS_QNT > 10);

    INSERT INTO USERS_STATISTICS VALUES (NULL, CURRENT_TIME, IFNULL(QNT, 0), IFNULL(CONFIRMED, 0),
                                         IFNULL(REGULAR_USERS, 0), IFNULL(ADMINS, 0), IFNULL(L_ACT, 0),
                                         IFNULL(M_ACT, 0), IFNULL(H_ACT, 0), IFNULL(T_AVT, 0),
                                         IFNULL(OPINIONS_LOWER_5, 0), IFNULL(OPINIONS_BETWEEN_5_10, 0),
                                         IFNULL(OPINIONS_HIGHER_10, 0), IFNULL(RATINGS_LOWER_5, 0),
                                         IFNULL(RATINGS_BETWEEN_5_10, 0), IFNULL(RATINGS_HIGHER_THEN_10, 0));
    COMMIT ;
end;

create
    definer = root@localhost function GameOpinionsPerDay(gameId mediumtext) returns double deterministic
BEGIN
    DECLARE result DOUBLE (5,2);
    DECLARE opinions_qnt, opinions_days INT;
    SELECT COUNT(*) FROM GAME_OPINIONS WHERE GAME_ID = gameId INTO opinions_qnt;
    SELECT DATEDIFF(CURRENT_DATE, MIN(GO.PUBLICATION_DATE)) + 1 FROM GAME_OPINIONS GO WHERE GAME_ID = gameId INTO opinions_days;

    SET result = opinions_qnt / opinions_days;

    IF (result IS NULL) THEN
        SET result = 0.0;
    END IF ;

    RETURN result;
end;

create
    definer = root@localhost function GamePopularityStatus(opinionsQntPerDay decimal, ratingsQntPerDay decimal) returns varchar(30) deterministic
BEGIN
    DECLARE result VARCHAR(30) DEFAULT 'LOW';
    IF opinionsQntPerDay > 10 OR  ratingsQntPerDay > 10 THEN
        SET result = 'ON THE TOP';
    ELSEIF opinionsQntPerDay > 7 AND opinionsQntPerDay <= 10 OR ratingsQntPerDay > 7 AND ratingsQntPerDay <= 10 THEN
        SET result = 'VERY HIGH';
    ELSEIF opinionsQntPerDay > 4 AND opinionsQntPerDay <= 7 OR ratingsQntPerDay > 4 AND ratingsQntPerDay <= 7 THEN
        SET result = 'HIGH';
    ELSEIF opinionsQntPerDay > 2 AND opinionsQntPerDay <= 4 OR ratingsQntPerDay > 2 AND ratingsQntPerDay <= 4 THEN
        SET result = 'MEDIUM';
    END IF;
    RETURN result;
END;

create
    definer = root@localhost function GameRatingsPerDay(gameId mediumtext) returns double deterministic
BEGIN
    DECLARE result DOUBLE (5,2);
    DECLARE ratings_qnt, ratings_days INT;
    SELECT COUNT(*) FROM GAME_RATINGS WHERE GAME_ID = gameId INTO ratings_qnt;
    SELECT DATEDIFF(CURRENT_DATE, MIN(GR.PUBLICATION_DATE)) + 1 FROM GAME_RATINGS GR WHERE GAME_ID = gameId INTO ratings_days;

    SET result = ratings_qnt / ratings_days;

    IF (result IS NULL) THEN
        SET result = 0.0;
    END IF ;

    RETURN result;
end;

create
    definer = root@localhost procedure UpdateGameOpinionsAndRatingsPerDay()
BEGIN
    DECLARE GM_ID INT;
    DECLARE FINISHED INT DEFAULT 0;
    DECLARE ALL_GAMES CURSOR FOR SELECT ID FROM GAMES;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET FINISHED = 1;

    OPEN ALL_GAMES;
    WHILE (FINISHED = 0) DO
            FETCH ALL_GAMES INTO GM_ID;
            IF (FINISHED = 0) THEN
                UPDATE GAMES SET GAMES.OPINIONS_PER_DAY = GameOpinionsPerDay(GM_ID), GAMES.RATINGS_PER_DAY = GameRatingsPerDay(GM_ID) WHERE ID = GM_ID;
                COMMIT ;
            END IF ;
        END WHILE ;
    CLOSE ALL_GAMES;
END;

create
    definer = root@localhost procedure UpdateGamePopularityStatus()
BEGIN
    DECLARE GM_ID INT;
    DECLARE OPINIONS_PER_DAY, RATINGS_PER_DAY DECIMAL(5,2);
    DECLARE FINISHED INT DEFAULT 0;
    DECLARE ALL_GAMES CURSOR FOR SELECT ID FROM GAMES;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET FINISHED = 1;

    OPEN ALL_GAMES;
    WHILE (FINISHED = 0) DO
            FETCH ALL_GAMES INTO GM_ID;
            IF (FINISHED = 0) THEN
                SET OPINIONS_PER_DAY = GameOpinionsPerDay(GM_ID);
                SET RATINGS_PER_DAY = GameRatingsPerDay(GM_ID);

                UPDATE GAMES SET POPULARITY_STATUS = GamePopularityStatus(OPINIONS_PER_DAY, RATINGS_PER_DAY)
                WHERE ID = GM_ID;
                COMMIT ;
            END IF ;
        END WHILE ;
    CLOSE ALL_GAMES;
END;

create
    definer = root@localhost procedure UpdateUserActivityStatus()
BEGIN
    DECLARE U_ID INT;
    DECLARE OPINIONS_PER_DAY, RATINGS_PER_DAY DECIMAL (5,2);
    DECLARE FINISHED INT DEFAULT 0;
    DECLARE ALL_USERS CURSOR FOR SELECT ID FROM  USERS;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET FINISHED = 1;

    OPEN ALL_USERS;
    WHILE (FINISHED = 0) DO
            FETCH ALL_USERS INTO U_ID;
            IF (FINISHED = 0) THEN
                SET OPINIONS_PER_DAY = UserOpinionsPerDay(U_ID);
                SET RATINGS_PER_DAY = UserRatingsPerDay(U_ID);

                UPDATE USERS SET ACTIVITY_STATUS = UserActivityStatus(OPINIONS_PER_DAY, RATINGS_PER_DAY)
                WHERE ID = U_ID;
                COMMIT ;
            end if;
        end while;
    CLOSE ALL_USERS;
end;

create
    definer = root@localhost procedure UpdateUserOpinionsAndRatingsPerDay()
BEGIN
    DECLARE U_ID INT;
    DECLARE FINISHED INT DEFAULT 0;
    DECLARE ALL_USERS CURSOR FOR SELECT ID FROM USERS;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET FINISHED = 1;

    OPEN ALL_USERS;
    WHILE (FINISHED = 0) DO
            FETCH ALL_USERS INTO U_ID;
            IF (FINISHED = 0) THEN
                UPDATE USERS SET USERS.OPINIONS_PER_DAY = UserOpinionsPerDay(U_ID), USERS.RATINGS_PER_DAY = UserRatingsPerDay(U_ID) WHERE ID = U_ID;
                COMMIT ;
            end if ;
        end while ;
    CLOSE ALL_USERS;
end;

create
    definer = root@localhost function UserActivityStatus(opinionsQntPerDay decimal, ratingsQntPerDay decimal) returns varchar(20) deterministic
BEGIN
    DECLARE result VARCHAR(20) DEFAULT 'LOW';
    IF opinionsQntPerDay > 5 OR ratingsQntPerDay > 5 THEN
        SET result = 'TOP';
    ELSEIF opinionsQntPerDay > 3 AND opinionsQntPerDay <= 5 OR ratingsQntPerDay > 3 AND ratingsQntPerDay <= 5 THEN
        SET result = 'HIGH';
    ELSEIF opinionsQntPerDay > 1 AND opinionsQntPerDay <= 3 OR ratingsQntPerDay > 1 AND ratingsQntPerDay <= 3 THEN
        SET result = 'MEDIUM';
    ELSE
        SET result = 'LOW';
    end if;
    RETURN result;
end;

create
    definer = root@localhost function UserOpinionsPerDay(userId mediumtext) returns double deterministic
BEGIN
    DECLARE result DOUBLE (5,2);
    DECLARE opinions_qnt, opinions_days INT;
    SELECT COUNT(*) FROM GAME_OPINIONS WHERE USER_ID = userId INTO opinions_qnt;
    SELECT DATEDIFF(MAX(GO.PUBLICATION_DATE), U.REGISTERED_DATE) + 1 FROM GAME_OPINIONS GO, USERS U WHERE GO.USER_ID AND U.ID = userId INTO opinions_days;

    SET result = opinions_qnt / opinions_days;

    IF (result IS NULL) THEN
        SET result = 0.0;
    END IF ;

    RETURN result;
end;

create
    definer = root@localhost function UserRatingsPerDay(userId mediumtext) returns double deterministic
BEGIN
    DECLARE result DOUBLE (5,2);
    DECLARE ratings_qnt, ratings_days INT;
    SELECT COUNT(*) FROM GAME_RATINGS WHERE USER_ID = userId INTO ratings_qnt;
    SELECT DATEDIFF(MAX(GR.PUBLICATION_DATE), U.REGISTERED_DATE) + 1 FROM GAME_RATINGS GR, USERS U WHERE GR.USER_ID AND U.ID = userId INTO ratings_days;

    SET result = ratings_qnt / ratings_days;

    IF (result IS NULL) THEN
        SET result = 0.0;
    END IF ;

    RETURN result;
end;

create definer = root@localhost event if not exists CREATE_NEW_GAMES_STATS on schedule
    every '1' HOUR
        starts '2022-05-22 23:03:22'
    enable
    do
    CALL CreateNewGamesStats();

create definer = root@localhost event if not exists CREATE_NEW_USERS_STATS on schedule
    every '1' HOUR
        starts '2022-05-23 13:18:02'
    enable
    do
    CALL CreateNewUsersStats();

create definer = root@localhost event if not exists UPDATE_GAMES_OPINIONS_AND_RATING_PER_DAY on schedule
    every '25' SECOND
        starts '2022-05-21 19:20:02'
    enable
    do
    CALL UpdateGameOpinionsAndRatingsPerDay();

create definer = root@localhost event if not exists UPDATE_GAMES_POP_STATUSES on schedule
    every '30' SECOND
        starts '2022-05-21 19:20:00'
    enable
    do
    CALL UpdateGamePopularityStatus();

create definer = root@localhost event if not exists UPDATE_USERS_OPINIONS_AND_RATINGS_PER_DAY on schedule
    every '27' SECOND
        starts '2022-05-22 15:58:53'
    enable
    do
    CALL UpdateUserOpinionsAndRatingsPerDay();

