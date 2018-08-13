package com.bol.controller;

import com.bol.model.Player;
import com.bol.service.PlayerService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class PlayerController {

    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @MessageMapping("/player/{gameId}")
    @SendTo("/topic/player/new/{gameId}")
    public Player createPlayer(@DestinationVariable String gameId, Player player) {
         return playerService.createPlayer(player.getName(), player.getTurn());
    }
}
