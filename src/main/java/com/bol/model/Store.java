package com.bol.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Store implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private Integer numberOfStones;

    public void addStones(Integer stones) {
        this.numberOfStones += stones;
    }
}
