package org.example;

public class PassLine extends Bet{
    private static final String PASS_LINE_NAME = "Pass Line Bet";
    public PassLine(int decisionValue, int betAmount) {
        super(decisionValue, betAmount, PASS_LINE_NAME);

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

            }
        }
        return 0;
    }


}
