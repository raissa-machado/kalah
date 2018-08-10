package com.bol.repository;

import com.bol.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface GameRepository extends CrudRepository<Game, UUID> {

}