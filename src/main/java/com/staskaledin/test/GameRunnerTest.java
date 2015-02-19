package com.staskaledin.test;

import com.staskaledin.main.Game;
import com.staskaledin.main.GameRunner;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameRunnerTest {

    private Game game;
    private GameRunner gameRunner;

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

        gameRunner = new GameRunner(game, 2);
    }

    @Test
    public void testRun() throws Exception {
        gameRunner.run();

        JSONObject out = gameRunner.getJsonStat();

        JSONArray summary = out.getJSONArray("summary");
        assertEquals(summary.getInt(0), 12);
        assertEquals(summary.getInt(1), 20);

    }
}