package com.bol.repository;

import com.bol.model.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


public interface PlayerRepository extends CrudRepository<Player, UUID> {

    Player findByName(String name);
}