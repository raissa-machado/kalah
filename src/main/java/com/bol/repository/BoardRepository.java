package com.bol.repository;

import com.bol.model.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BoardRepository extends CrudRepository<Board, UUID> {
}
