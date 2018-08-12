package com.bol.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;


@Data
@Entity
@Builder
public class Store implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private Integer numberOfStones;

    public void addStones(Integer stones) {
        this.numberOfStones += stones;
    }
}
