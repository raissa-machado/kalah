package com.bol.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.UUID;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Game implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @JoinColumn(name="board_id", referencedColumnName = "id")
    private Board board;
    private GameStatus gameStatus;
    private PlayerTurn playersTurn;

    public void changeTurn() {
        this.playersTurn = playersTurn.equals(PlayerTurn.FIRST) ? PlayerTurn.SECOND : PlayerTurn.FIRST;
    }
}
