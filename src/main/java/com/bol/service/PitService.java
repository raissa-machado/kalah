package com.bol.service;

import com.bol.model.Pit;
import com.bol.repository.PitRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;


@Service
public class PitService {

    private PitRepository pitRepository;

    public PitService(PitRepository pitRepository) {
        this.pitRepository = pitRepository;
    }

    @Transactional
    public ArrayList<Pit> createInitialArrayOfPits(Integer size) {
        ArrayList<Pit> pits = new ArrayList<>();
        for(int i=0; i<size; i++) {
            pits.add(pitRepository.save(Pit.builder().numberOfStones(size).build()));
        }
        return pits;
    }

    @Transactional
    public Integer emptyPit(Pit pit) {
        return pit.removeStones();
    }
}
