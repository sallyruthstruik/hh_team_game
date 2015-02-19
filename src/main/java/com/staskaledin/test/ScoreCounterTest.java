package com.staskaledin.test;

import com.staskaledin.main.PlayerActions;
import com.staskaledin.main.ScoreCounter;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreCounterTest {

    @Test
    public void testAllCooperates(){
        ScoreCounter sc = new ScoreCounter(PlayerActions.COOPERATE, PlayerActions.COOPERATE);

        assertEquals(1, sc.getPlayer1Score());
        assertEquals(1, sc.getPlayer2Score());
        assertEquals(2, sc.getSummaryScore());
    }

    @Test
    public void testFirstCooperates(){
        ScoreCounter sc = new ScoreCounter(PlayerActions.COOPERATE, PlayerActions.SWEAR);
        assertEquals(-2, sc.getPlayer1Score());
        assertEquals(2, sc.getPlayer2Score());
        assertEquals(0, sc.getSummaryScore());
    }

    @Test
    public void testSecondCooperates(){
        ScoreCounter sc = new ScoreCounter(PlayerActions.SWEAR, PlayerActions.COOPERATE);
        assertEquals(2, sc.getPlayer1Score());
        assertEquals(-2, sc.getPlayer2Score());
        assertEquals(0, sc.getSummaryScore());
    }

    @Test
    public void testAllSwear(){
        ScoreCounter sc = new ScoreCounter(PlayerActions.SWEAR, PlayerActions.SWEAR);
        assertEquals(-1, sc.getPlayer1Score());
        assertEquals(-1, sc.getPlayer2Score());
        assertEquals(-2, sc.getSummaryScore());
    }

}