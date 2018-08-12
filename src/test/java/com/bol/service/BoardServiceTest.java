package com.bol.service;

import com.bol.model.Board;
import com.bol.model.GameStatus;
import com.bol.model.Pit;
import com.bol.model.Player;
import com.bol.model.PlayerTurn;
import com.bol.repository.BoardRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class BoardServiceTest {

    @InjectMocks
    private BoardService boardService;
    @Mock
    private PlayerService playerService;
    @Mock
    private BoardRepository boardRepository;

    private Board board;

    private Player player1;

    private Player player2;

    @Before
    public void setUp() throws Exception {

        ArrayList<Pit> pits = new ArrayList<>(6);
        for(int i=0; i<6; i++) {
            pits.add(Pit.builder().numberOfStones(6).build());
        }
        player1 = Player.builder()
                .id(UUID.randomUUID())
                .name("Player 1")
                .turn(PlayerTurn.FIRST)
                .pits(pits)
                .build();
        player2 = Player.builder()
                .id(UUID.randomUUID())
                .name("Player 2")
                .turn(PlayerTurn.SECOND)
                .pits(pits)
                .build();
        board = Board.builder().player2(player2).player1(player1).build();
    }

    @Test
    public void shouldSowAndReturnContinue() throws Exception {
        when(playerService.findPlayerById(any())).thenReturn(player1);
        GameStatus status = boardService.sow(board, 0, player1.getId(), PlayerTurn.FIRST);
        Assert.assertEquals(GameStatus.CONTINUE, status);
    }

    @Test
    public void shouldSowAndReturnChangeTurn() throws Exception {
        ArrayList<Pit> pits = new ArrayList<>(6);
        for(int i=0; i<6; i++) {
            pits.add(Pit.builder().numberOfStones(0).build());
        }
        pits.get(0).setNumberOfStones(1);
        pits.get(1).setNumberOfStones(0);
        player1.setPits(pits);
        when(playerService.findPlayerById(any())).thenReturn(player1);
        GameStatus status = boardService.sow(board, 0, player1.getId(), PlayerTurn.FIRST);
        Assert.assertEquals(GameStatus.CHANGE_TURN, status);
    }

    @Test
    public void shouldSowAndReturnGameOverStatus() throws Exception {
        ArrayList<Pit> pits = new ArrayList<>(6);
        for(int i=0; i<6; i++) {
            pits.add(Pit.builder().numberOfStones(0).build());
        }
        player1.setPits(pits);
        when(playerService.findPlayerById(any())).thenReturn(player1);
        GameStatus status = boardService.sow(board, 0, player1.getId(), PlayerTurn.FIRST);
        Assert.assertEquals(GameStatus.GAME_OVER, status);
    }

    @Test
    public void shouldSowAndReturnIllegalPlayerStatus() throws Exception {
        when(playerService.findPlayerById(any())).thenReturn(player1);
        GameStatus status = boardService.sow(board, 0, player1.getId(), PlayerTurn.SECOND);
        Assert.assertEquals(GameStatus.ILLEGAL_PLAYER, status);
    }

    @Test
    public void shouldCollectAllPits() throws Exception {
        boardService.collectAllPits(board);
        verify(playerService).collectAllPits(player1);
        verify(playerService).collectAllPits(player2);
    }
}
