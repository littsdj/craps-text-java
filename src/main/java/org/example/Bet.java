package org.example;

public abstract class Bet {

    //decisionValue is the number that a bet needs to win or lose on.
    //this excludes 7, as all bets either win or lose on 7.
    //for example, a place bet on the 8 has a decisionValue of 8.
    //example, a don't pass bet with a point of 4 has a decisionValue of 4, even though it loses on 4.
    private int decisionValue;
    private int betAmount;
    private boolean isDead = false;

    public Bet(int decisionValue, int betAmount) {
        this.decisionValue = decisionValue;
        this.betAmount = betAmount;
    }

    public abstract int resolve(int diceRoll, int point);

    public int getDecisionValue() {
        return decisionValue;
    }

    public int getBetAmount() {
        return betAmount;
    }


    public boolean getIsDead() {
        return isDead;
    }

}
