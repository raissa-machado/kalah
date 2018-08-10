package com.bol.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Board implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="player_1_id", referencedColumnName = "id")
    private Player player1;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="player_2_id", referencedColumnName = "id")
    private Player player2;

}
