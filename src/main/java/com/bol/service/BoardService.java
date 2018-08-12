package com.bol.service;

import com.bol.model.Board;
import com.bol.model.Pit;
import com.bol.model.Player;
import com.bol.model.GameStatus;
import com.bol.model.PlayerTurn;
import com.bol.repository.BoardRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;


@Service
public class BoardService {

    private BoardRepository boardRepository;

    private PlayerService playerService;

    public BoardService(BoardRepository boardRepository, PlayerService playerService) {
        this.boardRepository = boardRepository;
        this.playerService = playerService;
    }

    @Transactional
    public Board createBoard(Player player1, Player player2) {
        return boardRepository.save(Board.builder().player1(player1).player2(player2).build());
    }

    @Transactional
    public GameStatus sow(Board board, Integer pitIndex, UUID playersId, PlayerTurn playersTurn) {
        Player player = playerService.findPlayerById(playersId);
        if (!player.getTurn().equals(playersTurn))
            return GameStatus.ILLEGAL_PLAYER;
        GameStatus gameStatus = sow(
                board.getPlayer1().getTurn().equals(playersTurn) ? board.getPlayer1() : board.getPlayer2(),
                pitIndex,
                board.getPlayer1().getTurn().equals(playersTurn) ? board.getPlayer2() : board.getPlayer1(),
                playersTurn);
        return isGameOver(board) ? GameStatus.GAME_OVER : gameStatus;
    }

    private GameStatus sow(Player currentPlayer, Integer pitIndex, Player opponentPlayer, PlayerTurn playersTurn) {
        int numberOfStones = currentPlayer.getPits().get(pitIndex).getNumberOfStones();
        currentPlayer.getPits().get(pitIndex).removeStones();
        pitIndex++;
        GameStatus gameStatus = GameStatus.CONTINUE;
        Boolean wasPitEmpty = Boolean.FALSE;

        while (numberOfStones > 0) {
            for (; pitIndex < 6 && numberOfStones > 0; pitIndex++, numberOfStones--) {
                wasPitEmpty = currentPlayer.getPits().get(pitIndex).getNumberOfStones().equals(0) ? Boolean.TRUE : Boolean.FALSE;
                currentPlayer.getPits().get(pitIndex).addStones(1);
            }

            if (numberOfStones == 0 && currentPlayer.getTurn().equals(playersTurn) && wasPitEmpty) {
                steal(currentPlayer, pitIndex-1, opponentPlayer, opponentsPitIndex(pitIndex-1));
                gameStatus = GameStatus.CHANGE_TURN;
            } else if (numberOfStones == 0) {
                gameStatus = GameStatus.CHANGE_TURN;
            } else if (numberOfStones >= 1 && currentPlayer.getTurn().equals(playersTurn)) {
                playerService.collectToStore(currentPlayer, 1);
                numberOfStones--;
                if (numberOfStones == 0) {
                    gameStatus = GameStatus.CONTINUE;
                }
            }

            Player temporaryPlayer = currentPlayer;
            currentPlayer = opponentPlayer;
            opponentPlayer = temporaryPlayer;
            pitIndex = 0;
        }
        return gameStatus;
    }

    private int opponentsPitIndex(Integer pitIndex) {
        return Math.abs(pitIndex - 5);
    }

    private void steal(Player player1, Integer pitIndex1, Player player2, Integer pitIndex2) {
        Integer collectedStones1 = playerService.removeStonesFromPit(player1, pitIndex1);
        Integer collectedStones2 = playerService.removeStonesFromPit(player2, pitIndex2);
        playerService.collectToStore(player1, collectedStones1 + collectedStones2);
    }

    private Boolean isGameOver(Board board) {
        return isEmptyPits(board.getPlayer1()) || isEmptyPits(board.getPlayer2()) ? Boolean.TRUE : Boolean.FALSE;
    }

    private Boolean isEmptyPits(Player player) {
        for (Pit pit : player.getPits()) {
            if (pit.getNumberOfStones() != 0)
                return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public void collectAllPits(Board board) {
        playerService.collectAllPits(board.getPlayer1());
        playerService.collectAllPits(board.getPlayer2());
    }


}
