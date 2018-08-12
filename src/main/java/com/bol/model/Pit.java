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
public class Pit implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private Integer numberOfStones;

    public Integer removeStones() {
        Integer removedStones = this.numberOfStones;
        this.numberOfStones = 0;
        return removedStones;
    }

    public void addStones(int stones) {
        this.numberOfStones += stones;
    }
}
