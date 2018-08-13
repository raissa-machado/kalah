package com.bol.service;

import com.bol.model.Game;
import com.bol.model.Player;
import com.bol.model.PlayerTurn;
import com.bol.model.Sow;
import com.bol.model.GameStatus;
import com.bol.repository.GameRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class GameService {

    private GameRepository gameRepository;
    private BoardService boardService;

    public GameService(GameRepository gameRepository, BoardService boardService) {
        this.gameRepository = gameRepository;
        this.boardService = boardService;
    }

    public Game createGame(Player player1, Player player2) {
        return gameRepository.save(Game.builder()
                .board(boardService.createBoard(player1, player2))
                .gameStatus(GameStatus.CONTINUE)
                .playersTurn(PlayerTurn.FIRST)
                .build());
    }

    public Game sow(Sow sow) {
        Game game = gameRepository.findById(sow.getGameId()).get();
        game.setGameStatus(boardService.sow(game.getBoard(), sow.getPitIndex(), sow.getPlayerId(), game.getPlayersTurn()));
        switch (game.getGameStatus()) {
            case CHANGE_TURN:
                game.changeTurn();
                game.setGameStatus(GameStatus.CONTINUE);
                break;
            case GAME_OVER:
                boardService.collectAllPits(game.getBoard());
                break;
        }
        return game;
    }
}
