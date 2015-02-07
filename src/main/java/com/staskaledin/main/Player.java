package com.staskaledin.main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

/**
 * Created by stas on 05.02.15.
 */
public interface Player {

    PlayerActions getAction(Player player);
    void addResult(Player partner, PlayerActions myAction, PlayerActions partnerAction);
    long getPlayerScore();

}

abstract class AbstractPlayer implements Player {

    private Map<Player, LinkedList<PlayerActions>> history = new HashMap<Player, LinkedList<PlayerActions>>();
    long summaryScore = 0;

    @Override
    public abstract PlayerActions getAction(Player player);

    @Override
    public void addResult(Player partner, PlayerActions myAction, PlayerActions partnerAction) {
        if(history.get(partner) == null){
            history.put(partner, new LinkedList<PlayerActions>());
        }
        history.get(partner).addLast(partnerAction);
        summaryScore += new ScoreCounter(myAction, partnerAction).getPlayer1Score();

    }

    protected PlayerActions getLastPartnerAction(Player partner){
        LinkedList<PlayerActions> list;
        if((list = history.get(partner)) != null){
            return list.getLast();
        }
        return null;
    }

    public long getPlayerScore() {
        return summaryScore;
    };
}

class AlwaysCooperate extends AbstractPlayer{
    @Override
    public PlayerActions getAction(Player player) {
        return PlayerActions.COOPERATE;
    }
}

class AlwaysSwear extends AbstractPlayer{
    @Override
    public PlayerActions getAction(Player player) {
        return PlayerActions.SWEAR;
    }
}

class RandomAction extends AbstractPlayer{
    float coorepatePossibility;

    public RandomAction(float coorepatePossibility) {
        if(Float.compare(coorepatePossibility, 0) <= 0 ||
                Float.compare(coorepatePossibility, 1)> 0)
            throw new IllegalArgumentException();

        this.coorepatePossibility = coorepatePossibility;
    }

    @Override
    public PlayerActions getAction(Player player) {
        Random rand = new Random();
        if(Float.compare(rand.nextFloat(), coorepatePossibility) < 0)
            return PlayerActions.COOPERATE;
        else
            return PlayerActions.SWEAR;
    }
}

class GoodVendettaPlayer extends AbstractPlayer{

    @Override
    public PlayerActions getAction(Player player) {
        PlayerActions partnerAction;
        if(null == (partnerAction = getLastPartnerAction(player)))
            return defaultAction();
        else
            return partnerAction;
    }

    protected PlayerActions defaultAction() {
        return PlayerActions.COOPERATE;
    }
}

class BadVendettaPlayer extends GoodVendettaPlayer{
    @Override
    protected PlayerActions defaultAction() {
        return PlayerActions.SWEAR;
    }
}