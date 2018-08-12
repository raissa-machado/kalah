package com.bol.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Data
@Builder
public class Sow {
    private UUID gameId;
    private UUID playerId;
    private int pitIndex;
}
