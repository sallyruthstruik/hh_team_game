package com.staskaledin.main;

/**
 * Created by stas on 05.02.15.
 */
public class ScoreCounter {

    PlayerActions action1, action2;

    long [] scores;

    public ScoreCounter(PlayerActions action1, PlayerActions action2) {
        this.action1 = action1;
        this.action2 = action2;
        scores = getScores();
    }

    /*
    * Возвращает счета первого и второго игрока в зависимости от их действий
     */
    private long[] getScores(){
        if(action1 == PlayerActions.COOPERATE && action2 == PlayerActions.COOPERATE)
            return new long[]{5, 5};
        else if(action1 == PlayerActions.COOPERATE && action2 == PlayerActions.SWEAR)
            return new long[]{0, 6};
        else if(action1 == PlayerActions.SWEAR && action2 == PlayerActions.COOPERATE)
            return new long[]{6, 0};
        else if(action1 == PlayerActions.SWEAR && action2 == PlayerActions.SWEAR)
            return new long[]{3, 3};
        else
            throw new IllegalStateException();
    }

    public long getPlayer1Score(){
        return scores[0];
    }

    public long getPlayer2Score(){
        return scores[1];
    }

    public long getSummaryScore(){
        return scores[0] + scores[1];
    }
}
