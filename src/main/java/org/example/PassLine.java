package org.example;

public class PassLine extends Bet{
    private static final String PASS_LINE_NAME = "Pass Line Bet";
    public PassLine(int betAmount) {
        super(0, betAmount, PASS_LINE_NAME);

    }

    @Override
    public int resolve(int diceRoll, int point) {

        if (point == 0) {
            switch (diceRoll){

                case 2:
                case 3:
                case 12:
                    announceBetLoss();
                    isDead = true;
                    return 0;
                case 7:
                case 11:
                    return announceWinOptions(betAmount);
                case 4:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                    decisionValue = diceRoll;
                    return 0;
            }
        }
        if (point != 0){
            if (diceRoll == 7){
                announceBetLoss();
                isDead = true;
                return 0;
            }
            if (diceRoll == point){
                return announceWinOptions(betAmount);
            }
        }
        return 0;
    }

    public boolean pullBet(){
        if (decisionValue != 0){
            System.out.println("This bet is already locked in.");
            return false;
        }else {
            System.out.println("Your " + betName + " has been pulled.");
            return true;
        }
    }


}
