package com.staskaledin.main;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ws.rs.BadRequestException;
import java.util.*;

/**
 * Created by stas on 06.02.15.
 */
public class Game {

    private List<Player> players = new ArrayList<Player>();
    private long totalGameScore;

    public long getTotalGamesCount() {
        return totalGamesCount;
    }

    public int getPlayersCount(){
        return players.size();
    }

    private long totalGamesCount;
//    private Map<? extends Player, Long> playerScores;


    private Game() {
    }

    private void addPlayer(Player player){
        players.add(player);
    }

    private Iterator<List<Player>> allIterator(){
        return new Iterator<List<Player>>() {
            int i=0, j=1;

            @Override
            public boolean hasNext() {
                return i<players.size() - 1 && j < players.size();
            }

            @Override
            public List<Player> next() {
                try {
                    return Arrays.asList(new Player[]{players.get(i), players.get(j)});
                }finally {
                    if(j == players.size() - 1){
                        i ++;
                        j = i+1;
                    }else{
                        j++;
                    }
                }
            }

            @Override
            public void remove() {
                throw new NotImplementedException();
            }
        };
    }

    public void doStep(){
        System.out.println("Count players "+players.size());

        Iterator<List<Player>> it = allIterator();

        while(it.hasNext()) {
            List<Player> pair = it.next();

            Player player1 = pair.get(0);
            Player player2 = pair.get(1);

            PlayerActions player1Action = player1.getAction(player2);
            PlayerActions player2Action = player2.getAction(player1);

            ScoreCounter sc = new ScoreCounter(player1Action, player2Action);

            totalGameScore += sc.getSummaryScore();
            totalGamesCount += 1;

            player1.addResult(player2, player1Action, player2Action);
            player2.addResult(player1, player2Action, player1Action);
        }


    }
    public long getSummaryScore(){
        return totalGameScore;
    }
//    public double getAverageScoreForType(Class<T> type);

    public static class Builder{
        long countCooperate, countSwear, countRandom, countGoodV, countWithMemory;
        float cooperateProbability = 0.5f;

        public Builder setCountCooperate(long countCooperate) {
            this.countCooperate = countCooperate;
            return this;
        }

        public Builder setCountSwear(long countSwear) {
            this.countSwear = countSwear;
            return this;
        }

        public Builder setCountRandom(long countRandom) {
            this.countRandom = countRandom;
            return this;
        }

        public Builder setCountGoodV(long countGoodV) {
            this.countGoodV = countGoodV;
            return this;
        }

        public Builder setCountWithMemory(long countWithMemory) {
            this.countWithMemory = countWithMemory;
            return this;
        }

        public Builder setCooperateProbability(float cooperateProbability) {
            if(Float.compare(cooperateProbability, 0) <= 0 || Float.compare(cooperateProbability, 1) >= 1)
                throw new IllegalArgumentException("Incorrect probability value");

            this.cooperateProbability = cooperateProbability;
            return this;
        }

        public Game build(){
            Game game = new Game();

            if(countCooperate > 0){
                for(int i=0; i<countCooperate;i++)
                    game.addPlayer(new AlwaysCooperate());
            }
            if(countSwear > 0){
                for(int i=0; i<countSwear; i++)
                    game.addPlayer(new AlwaysSwear());
            }
            if(countRandom > 0){
                for(int i=0; i<countRandom; i++)
                    game.addPlayer(new RandomAction(cooperateProbability));
            }
            if(countGoodV > 0){
                for(int i=0; i<countGoodV;i++)
                    game.addPlayer(new GoodVendettaPlayer());
            }
            if(countWithMemory > 0){
                for(int i=0; i< countWithMemory; i++)
                    game.addPlayer(new PlayerWithMemory());
            }

            if(game.players.size() <= 1)
                throw new BadRequestException("You must add at least 2 player");
            if(game.players.size() > 200)
                throw new BadRequestException("Max players count in command is 200");

            return game;
        }
    }


}
