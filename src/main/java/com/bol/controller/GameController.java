package com.bol.controller;

import com.bol.model.Game;
import com.bol.model.Player;
import com.bol.model.Sow;
import com.bol.service.GameService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class GameController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @MessageMapping("/game/{gameId}")
    @SendTo("/topic/game/new/{gameId}")
    public Game createGame(@DestinationVariable String gameId, List<Player> players) {
        return gameService.createGame(players.get(0), players.get(1));
    }

    @MessageMapping("/sow/{gameId}")
    @SendTo("/topic/game/sow/{gameId}")
    public Game sowStones(@DestinationVariable String gameId, Sow sow) {
       return gameService.sow(sow);
    }

}
