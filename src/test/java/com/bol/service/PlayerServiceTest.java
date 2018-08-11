package com.bol.service;

import com.bol.model.Pit;
import com.bol.model.Player;
import com.bol.model.PlayerTurn;
import com.bol.model.Store;
import com.bol.repository.PlayerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
public class PlayerServiceTest {

    @InjectMocks
    private PlayerService playerService;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private PitService pitService;
    @Mock
    private StoreService storeService;
    private Player player;
    private Store store;

    @Before
    public void setUp() throws Exception {
        ArrayList<Pit> pits = new ArrayList<>(6);
        for(int i=0; i<6; i++) {
            pits.add(new Pit(UUID.randomUUID(), 6));
        }
        store = new Store(UUID.randomUUID(), 0);
        player = Player.builder()
                .id(UUID.randomUUID())
                .name("Teste")
                .pits(pits)
                .store(store)
                .turn(PlayerTurn.FIRST)
                .build();
    }

    @Test
    public void shouldCreatePlayer() throws Exception {
        playerService.createPlayer("Test Player", PlayerTurn.FIRST);
        verify(playerRepository).save(any());
    }

    @Test
    public void shouldCollectAllPits() throws Exception {
        playerService.collectAllPits(player);
        verify(pitService, times(6)).emptyPit(any());
        verify(storeService).addStones(store, 36);
    }

    @Test
    public void shouldRemoveStonesFromPit() throws Exception {
        playerService.removeStonesFromPit(player, 3);
        verify(pitService).emptyPit(player.getPits().get(3));
    }

    @Test
    public void shouldCollectToStore() throws Exception {
        playerService.collectToStore(player, 36);
        verify(storeService).addStones(store, 36);
    }
}
