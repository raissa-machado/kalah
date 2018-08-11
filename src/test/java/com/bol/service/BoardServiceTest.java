package com.bol.service;

import com.bol.model.Board;
import com.bol.model.Player;
import com.bol.model.PlayerTurn;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;


@RunWith(SpringRunner.class)
public class BoardServiceTest {

    @InjectMocks
    private BoardService boardService;
    @Mock
    private PlayerService playerService;

    @Test
    public void shouldSowAndReturnPlayer2Turn() throws Exception {

    }

    @Test
    public void shouldSowAndReturnPlayer1Turn() throws Exception {
    }

    @Test
    public void shouldSowAndReturnGameOverStatus() throws Exception {

    }

    @Test
    public void shouldCollectAllPits() throws Exception {
        Player player1 = Player.builder()
                .id(UUID.randomUUID())
                .name("Player 1")
                .turn(PlayerTurn.FIRST)
                .build();
        Player player2 = Player.builder()
                .id(UUID.randomUUID())
                .name("Player 2")
                .turn(PlayerTurn.SECOND)
                .build();
        Board board = new Board(UUID.randomUUID(), player1, player2);
        boardService.collectAllPits(board);
        Mockito.verify(playerService).collectAllPits(player1);
        Mockito.verify(playerService).collectAllPits(player2);
    }
}
