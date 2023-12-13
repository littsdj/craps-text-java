package org.example;

public class PlaceBet extends Bet {


    public PlaceBet(int decisionValue, int betAmount){
        super(decisionValue, betAmount, "Place Bet on " + decisionValue);
    }

    @Override
    public int resolve(int diceRoll, int point) {
        if (point != 0){
            if (diceRoll == 7){
                announceBetLoss();
                isDead = true;
                return 0;
            }

            if (diceRoll == decisionValue){
                int betWin = 0;

                //determines how much the bet won
                switch (decisionValue){
                    case 4:
                    case 10:
                        betWin = (betAmount * 9) / 5;
                        break;
                    case 5:
                    case 9:
                        betWin = (betAmount * 7) / 5;
                        break;
                    case 6:
                    case 8:
                        betWin = (betAmount * 7) / 6;
                        break;
                }
                return announceWinOptions(betWin);
            }

        }
        return 0;

    }
}
