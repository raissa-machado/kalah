package com.bol.service;

import com.bol.model.Store;
import com.bol.repository.StoreRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;


@Service
public class StoreService {

    private StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Transactional
    public void addStones(Store store, Integer stones) {
        store.addStones(stones);
    }

    @Transactional
    public Store createInitialStore() {
        return storeRepository.save(new Store(UUID.randomUUID(), 0));
    }
}
