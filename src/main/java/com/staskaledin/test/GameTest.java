package com.staskaledin.test;

import com.staskaledin.main.Game;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameTest {

    private Game game;

    @Before
    public void setUp() throws Exception {
        game = new Game.Builder()
                .setCountSwear(1)
                .setCountCooperate(1)
                .setCountGoodV(1)
                .setCountRandom(1)
                .setCooperateProbability((float) 1)
                .setCountWithMemory(1)
                .build();

    }

    @Test
    public void testDoStep() throws Exception {
        game.doStep();
        assertEquals(5, game.getPlayersCount());
        assertEquals(10, game.getTotalGamesCount());
        assertEquals(12, game.getSummaryScore());

        game.doStep();
        assertEquals(5, game.getPlayersCount());
        assertEquals(20, game.getTotalGamesCount());
        assertEquals(20, game.getSummaryScore());

    }

}