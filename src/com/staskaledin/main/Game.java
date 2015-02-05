package com.staskaledin.main;

/**
 * Created by stas on 05.02.15.
 */
interface Game {

    void addPlayer(Player player);
    void runGame();
    long getSummaryScore(int epochNumber);

}


public class GameWithStat implements Game{

}