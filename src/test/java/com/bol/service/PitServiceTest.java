package com.bol.service;

import com.bol.model.Pit;
import com.bol.repository.PitRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class PitServiceTest {

    @InjectMocks
    private PitService pitService;

    @Mock
    private PitRepository pitRepository;

    @Test
    public void shouldCreateInitialArrayOfPitsWithGivenSize() throws Exception {

        ArrayList<Pit> pits = pitService.createInitialArrayOfPits(3);
        assertEquals(3, pits.size());

    }
}
