package com.staskaledin.main;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stas on 06.02.15.
 */
public class GameRunner {

    private Game game;
    private int countEpochs;
    private List<Long> summaryStat = new ArrayList<Long>();
    private List<Double> productivityStat = new ArrayList<Double>();

    public GameRunner(Game game, int countEpochs){
        this.game = game;
        this.countEpochs = countEpochs;
    };

    public GameRunner run(){
        for(int i=0; i<countEpochs; i++){
            game.doStep();
//            summaryStat.add(game.getSummaryScore()/game.getPlayersCount());
            summaryStat.add(game.getSummaryScore());

            productivityStat.add((double) game.getSummaryScore()/game.getTotalGamesCount());
        }
        return this;
    }

    public JSONObject getJsonStat(){
        try {
            JSONObject out = new JSONObject();
            out.put("summary", summaryStat);
            out.put("productivity", productivityStat);
            return out;
        }catch (JSONException e){
            System.err.println("Json exception: " + e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] arga){
        Game game = new Game.Builder()
                .setCountCooperate(10)
                .setCountSwear(10)
                .build();
        System.out.println(new GameRunner(game, 100).run().getJsonStat());
    }

}
