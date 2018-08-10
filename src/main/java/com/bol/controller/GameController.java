package com.bol.controller;

import com.bol.model.Game;
import com.bol.model.Player;
import com.bol.model.Sow;
import com.bol.service.GameService;
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

    @MessageMapping("/game")
    @SendTo("/topic/game/new")
    public Game createGame(List<Player> players) {
        return gameService.createGame(players.get(0), players.get(1));
    }

    @MessageMapping("/sow")
    @SendTo("/topic/game/sow")
    public Game sowStones(Sow sow) {
       return gameService.sow(sow);
    }

}
