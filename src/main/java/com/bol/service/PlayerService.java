package com.bol.service;

import com.bol.model.Pit;
import com.bol.model.Player;
import com.bol.model.PlayerTurn;
import com.bol.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;


@Service
public class PlayerService {

    private PlayerRepository playerRepository;
    private PitService pitService;
    private StoreService storeService;

    public PlayerService(PlayerRepository playerRepository, PitService pitService, StoreService storeService) {
        this.playerRepository = playerRepository;
        this.pitService = pitService;
        this.storeService = storeService;
    }

    @Transactional
    public Player createPlayer(String name, PlayerTurn turn) {
        return playerRepository.save(Player.builder()
                .name(name)
                .turn(turn)
                .pits(pitService.createInitialArrayOfPits(6))
                .store(storeService.createInitialStore())
                .build());
    }

    @Transactional
    public Player findPlayerById(UUID id) {
        return playerRepository.findById(id).get();
    }

    public void collectAllPits(Player player){
        int collectedStones = 0;
        for (Pit pit : player.getPits()) {
            collectedStones += pit.getNumberOfStones();
            pitService.emptyPit(pit);
        }
        collectToStore(player, collectedStones);
    }

    public Integer removeStonesFromPit(Player player, int pitIndex) {
        return pitService.emptyPit(player.getPits().get(pitIndex));
    }

    public void collectToStore(Player player, Integer numberOfCollectedStones) {
        storeService.addStones(player.getStore(), numberOfCollectedStones);
    }
}
