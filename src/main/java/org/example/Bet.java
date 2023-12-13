package org.example;

public abstract class Bet {

    //decisionValue is the number that a bet needs to win or lose on.
    //this excludes 7, as all bets either win or lose on 7.
    //for example, a place bet on the 8 has a decisionValue of 8.
    //example, a don't pass bet with a point of 4 has a decisionValue of 4, even though it loses on 4.
    protected int decisionValue;
    protected int betAmount;
    protected boolean isDead = false;
    protected String betName;

    private String decisionString;

    public Bet(int decisionValue, int betAmount, String betName) {
        this.decisionValue = decisionValue;
        this.betAmount = betAmount;
        this.betName = betName;
        this.decisionString = "Your " + this.betName + " of " + this.betAmount + " dollars ";
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
        this.decisionString = "Your " + this.betName + " of " + this.betAmount + " dollars ";
    }

    public abstract int resolve(int diceRoll, int point);

    protected void announceBetLoss(){

        System.out.println(decisionString + "lost...");
    }

    protected int announceWinOptions(int winPayout) {
        System.out.println(decisionString + " won " + winPayout + " dollars!");
        String options = "You Can: \n --- 1 Same Bet\n --- 2 Pull Bet\n" +
                " --- 3 Increase Bet\n --- 4 Double Bet";
        int pressOption = GameOperationCLI.getUserChoice(4, options);
        switch (pressOption){
            case 1:
                return winPayout;
            case 2:
                isDead = true;
                return winPayout + betAmount;
            case 3:
                int pressureAmount = GameOperationCLI.getUserChoice(winPayout, "How much to increase?");
                setBetAmount(betAmount + pressureAmount);
                return winPayout - pressureAmount;
            case 4:
                setBetAmount(betAmount * 2);
                return winPayout - (betAmount / 2);
        }
        return winPayout;
    }

    public int getDecisionValue() {
        return decisionValue;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public boolean pullBet(){
        System.out.println("Your " + betName + " has been pulled.");
        return true;
    }

    public boolean getIsDead() {
        return isDead;
    }

    public String getBetName() {
        return betName;
    }

    public void setBetName(String betName) {
        this.betName = betName;
    }

}
