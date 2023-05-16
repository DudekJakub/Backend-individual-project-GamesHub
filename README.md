Hi!

ADMISSION WORD:

My name is Jakub Dudek and I would like to present you my own REST API application.
It is called GamesHub and its main task is to provide the user with simple and intuitive tools for browsing both computer games (as the name suggests) and related, perhaps interesting books, comics, guides or art-books.

Where did the idea come from? Well, first of all, because there are few light and transparent applications that actually match my taste, which would allow me to quickly and intuitively find the game I am interested in and leave some symbolic mark in the form of an opinion about a given title or a personal assessment. ;)

Therefore, despite the insufficient amount of time to complete the project, I intend to develop my idea further. In the future, I'd like to add a simple front-end and implement all the mechanisms that came to my mind while 'tapping' the code (and there are a few of them).

<br><br>

PROJECT CONFIGURATION DESCRIPTION:

The project requires three elements to work properly:

1. Supplementing the GAMES table with the records provided in src/main/resources data.sql (this is about 4300 records that are the core of the application)

2. Supplementing the database with 6 procedures / 6 functions / 5 events and 14 triggers -> as above, everything is prepared in the appropriate file, here schema.sql

In the src/main/resources/application.properties file, commands to initialize the above files have been prepared.
I think that my project will be checked by a person who has cosmically higher competences to explain the whole process, so I will end the topic here. :)
<br>
*Additionally (in case of problems with generating DDL scripts) I included 'GamesHub_importantSchema' file with 'raw' DDL scripts.

<br>
Why 4300 records? There are 3 reasons:

- By storing in the database basic information containing ID and the so-called *slug-name, I am able to directly ask external API to provide a specific JSON with the game.

- The above solution is also much faster than filtering multiple pages containing many more or less matched games (filtering didn't work well enough unfortunately)

- I can join the functionality table for users at the same time. This is how I create a kind of wrapper for an external API.

*slug-name is a string ID of games in rawgApi. Locally converted from 'iron-maiden' to Iron Maiden.

<br>
Minuses:

- The database is in the pre-development phase, so the number of games to be searched directly is very limited.
For this reason, the next step will be the self-completion of the database with the records that are not present in it.

<br>
3. EMAIL! This is a very important point because GitHub blocks the sharing of sensitive data such as passwords. Gmail automatically blocks access to services.
By default, the application is set to use GMAIL.

<br><br>

APP DESCRIPTION:

The app includes:

10 controllers :
- AppNotificationC
- BookC
- GameC
- GameOpinionC
- GameRatingC
- GoogleBookC
- RawgGameC
- RegistrationC
- StatisticC
- UserC

4 design patterns:
- Observer
- Strategy
- Facade
- Visitor

4 different emails for user/admin (variation in HTML):
- User stats mail
- Admin stats mail
- User confirmation mail
- User newOpinion mail
- User newRating mail

3 ways of user notification:
- FullEmailNotif
- SimpleEmailNotif
- InsideAppNotif

3 external REST APIs:
- RawgGame (core of the whole application)
- GoogleBook
- EmailVerificator

And others:

+ 2 schedulers
+ 10 validators (mainly validation after user-confirmation-email)
+ connected h2 database for tests
+ simple implementation of Spring Security
+ 6 sql procedures
+ 6 sql of features
+ 14 sql triggers
+ 5 sql of events
+ globalHttpErrorHandler
+ a system for collecting statistics on 'traffic' in the application

Project for May 25, 2022 at 23:41 fully builds with Gradle builder.

<br><br>

AT THE END:

What did I miss?:

1. More tests. Unfortunately, I did not have a day to complete this very important component of the application... Completing the tests will certainly be a priority before further development of the application.

2. Time for Front-End -> although I'm not particularly interested in it, the visualization of the project, even in the simplest form, is a nice plus.

3. Self-monitoring and self-assessment -> at some point I wanted to implement all ideas, which resulted in a to-do list (reasonably now looking) for a week, instead of 2/3 days.

<br>

Due to the fact that this is the last day of the Kodilla course, I would like to thank myself first of all for perseverance during hundreds of hours of learning programming over the last few months. Thanks also to the entire Kodilla team, it was a fun adventure. ;)

And for future students who may be here someday and will read my scribbles... exhaustion! Programming is not easy, but it is quite logical. good luck!
