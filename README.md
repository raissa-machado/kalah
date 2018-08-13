# Kalah Game

Web application that implements the Kalah Game.

## Rules

1. At the beginning of the game, six stones are placed in each pit.
2. Each player controls the six pits and their stones on the player's side of the board. The player's score is the number of stones in the store to their right.
3. Players take turns sowing their stones. At each turn, the player removes all stones from one of the pits under their control. Moving counter-clockwise, the player drops one stone in each pit, including the player's own store but not their opponent's.
4. If the last stone lands in an empty pit owned by the player, the player captures his own stone and all stones in the opposite pit and puts them in his own store.
5. If the last stone lands in the player's store, the player gets an additional move. There is no limit on the number of moves a player can make in their turn.
6. When one player no longer has any stones in any of their pits, the game ends. The other player moves all remaining stones to their store, and the player with the most stones in their store wins.

## Run

Running the tests: <br/>
    `mvn clean install`

Running the application: <br/>
    `mvn spring-boot:run`

## Play

Then the game can be accessed at: http://localhost:8080 <br/>
Each browser tab will host a game, it is possible to play multiple games at once. <br/>
When the game is over the players could start a new one immediately or enter new players before re-starting.

## New Features

Possible improvements to the current application:
* Choosing the number of initial stones of the game;
* Tracking scores through multiple games;
* Building a game room, where players could choose a particular game to enter;
* Adding artificial intelligence;

## Stack
* Java
* SpringBoot
* SockJS
* HTML5
* CSS
* JavaScript
* JQuery