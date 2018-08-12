package com.bol.service;

import com.bol.model.Store;
import com.bol.repository.StoreRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class StoreServiceTest {

    @InjectMocks
    private StoreService storeService;

    @Mock
    private StoreRepository storeRepository;

    @Test
    public void shouldAddNumberOfStones() throws Exception {

        Store store = Store.builder().numberOfStones(0).build();
        storeService.addStones(store, 4);

        assertEquals(java.util.Optional.of(4), java.util.Optional.ofNullable(store.getNumberOfStones()));

    }
}
