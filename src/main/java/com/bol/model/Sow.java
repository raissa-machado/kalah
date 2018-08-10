package com.bol.model;

import lombok.Data;

import java.util.UUID;


@Data
public class Sow {
    private UUID gameId;
    private UUID playerId;
    private int pitIndex;
}
