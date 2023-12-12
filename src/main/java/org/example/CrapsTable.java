package org.example;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Designed for single player use. I might add some capabilities later that would allow multiplayer support
//such as having an array of Player objects, but for now, I will assume only 1 player at the table.


public class CrapsTable {
    private List<Bet> playerBets = new ArrayList<>();
    private int playerBankroll;
    private int currentRoll;

    //instead of having a boolean for the comeout roll, a point of 0 will indicate that the next roll is the comeout roll
    private int point = 0;
    private Random roller = new Random();


    //nextRoll takes in an int so I can take out randomness for testing
    public void nextRoll(int roll){
        //if roll == 0, generate random roll. otherwise, the roll is whatever the input is
        //purely for testing purposes
        if (roll > 0){
            currentRoll = roll;
        } else{
            currentRoll = rollDice();
        }

        System.out.println("The call is " + currentRoll);

        //if this is the comeout roll
        if (point == 0){
            switch (currentRoll) {
                //natural decisions on comeout rolls
                case 2:
                case 3:
                case 7:
                case 11:
                case 12:
                    resolveBets();
                    break;
                    //point is established
                case 4:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                    resolveBets();
                    point = currentRoll;
            }
        }
        //we have a point, resolve bets then change point to 0 if it was a decision roll
        else {
           resolveBets();
           if (currentRoll == 7 || currentRoll == point){
               point = 0;
           }
        }
    }

    private void resolveBets() {
        for(Bet bet : playerBets) {
            int resolutionChange = bet.resolve(currentRoll, point);
            playerBankroll += resolutionChange;
            //if bet no longer exists, remove it from the bet list
            if (bet.getIsDead()){
                playerBets.remove(bet);
            }

        }
    }

    private int rollDice(){
        int die1 = roller.nextInt(5) + 1;
        int die2 = roller.nextInt(5) + 1;
        return die1 + die2;
    }


}
