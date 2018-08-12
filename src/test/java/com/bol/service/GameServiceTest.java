package com.bol.service;

import com.bol.model.Board;
import com.bol.model.Game;
import com.bol.model.GameStatus;
import com.bol.model.Player;
import com.bol.model.PlayerTurn;
import com.bol.model.Sow;
import com.bol.repository.GameRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class GameServiceTest {

    @InjectMocks
    private GameService gameService;
    @Mock
    private BoardService boardService;
    @Mock
    private GameRepository gameRepository;
    private Game game;

    @Before
    public void setUp() throws Exception {
        game =  Game.builder()
                .board(Board.builder().player1(Player.builder().build()).player2(Player.builder().build()).build())
                .gameStatus(GameStatus.CONTINUE)
                .playersTurn(PlayerTurn.FIRST)
                .build();
    }

    @Test
    public void shouldSowAndReturnContinue() throws Exception {
        when(gameRepository.findById(game.getId())).thenReturn(Optional.of(game));
        when(boardService.sow(any(), any(), any(), any())).thenReturn(GameStatus.CHANGE_TURN);
        game = gameService.sow(Sow.builder().gameId(game.getId()).pitIndex(0).build());
        Assert.assertEquals(GameStatus.CONTINUE, game.getGameStatus());
    }

    @Test
    public void shouldSowAndReturnGameOver() throws Exception {
        when(gameRepository.findById(game.getId())).thenReturn(Optional.of(game));
        when(boardService.sow(any(), any(), any(), any())).thenReturn(GameStatus.GAME_OVER);
        game = gameService.sow(Sow.builder().gameId(game.getId()).pitIndex(0).build());
        Assert.assertEquals(GameStatus.GAME_OVER, game.getGameStatus());
    }

    @Test
    public void shouldSowAndReturnIllegalPlayer() throws Exception {
        when(gameRepository.findById(game.getId())).thenReturn(Optional.of(game));
        when(boardService.sow(any(), any(), any(), any())).thenReturn(GameStatus.ILLEGAL_PLAYER);
        game = gameService.sow(Sow.builder().gameId(game.getId()).pitIndex(0).build());
        Assert.assertEquals(GameStatus.ILLEGAL_PLAYER, game.getGameStatus());
    }
}
