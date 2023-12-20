package org.example;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Designed for single player use. I might add some capabilities later that would allow multiplayer support
//such as having an array of Player objects, but for now, I will assume only 1 player at the table.


public class CrapsTable {
    private List<Bet> playerBets = new ArrayList<>();
    private final String FILLER_LINE = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
    private int playerBankroll;
    private int currentRoll;

    //instead of having a boolean for the comeout roll, a point of 0 will indicate that the next roll is the comeout roll
    private int point = 0;

    private Random roller = new Random();

    public CrapsTable(int startingBankroll){
        this.playerBankroll = startingBankroll;
    }


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
        int i = 0;
        while (i < playerBets.size()){
            Bet currentBet = playerBets.get(i);
            int resolutionChange = currentBet.resolve(currentRoll, point);
            playerBankroll += resolutionChange;
            //if bet no longer exists, remove it from the bet list
            if (currentBet.getIsDead()){
                playerBets.remove(currentBet);
            } else{
                i ++;
            }

        }
    }

    private int rollDice(){
        int die1 = roller.nextInt(5) + 1;
        int die2 = roller.nextInt(5) + 1;
        return die1 + die2;
    }

    public void prepareForRoll(){
        String announcement;
        if (point == 0){
            announcement = "This is the come-out roll. Pass-Line and Don't Pass bets are open!\n" +
                    "Place Bets are off on the come out";
            System.out.println(announcement);
            manageBets(true);

        }else {
            announcement = "The point is " + point + ".";// +
          //          "\nLine Bets are locked in. Place bets are open!\n" +
          //          "You can also increase or pull bets.";
            System.out.println(announcement);
            manageBets(false);
        }
    }

    private void manageBets(boolean isComeOutRoll) {
        while (true){
            String options = FILLER_LINE + "Your bankroll is " + playerBankroll +
                    "\n1 -- Make/Increase a bet!\n" +
                    "2 -- Take down a bet\n" +
                    "3 -- List my bets\n" +
                    "4 -- Let's Roll!\n" +
                    "5 -- help (not yet supported)";
            int userChoice = GameOperationCLI.getUserChoice(5, options);
            switch (userChoice) {
                case 1:
                    makeBet(isComeOutRoll);
                    break;
                case 2:
                    removeBet();
                    break;
                case 3:
                    listBets();
                    break;
                case 4:
                    return;
                case 5:
                    System.out.println("Help feature will be available soon");
                    break;
            }
        }

    }

    private void makeBet(boolean isComeOutRoll) {
        if (isComeOutRoll){
            String options = FILLER_LINE + "Make a Bet\n" +
                    "1 -- Pass-Line Bet\n" +
                    "2 -- Don't Pass Bet\n" +
                    "3 -- Previous menu";
            int betChoiceNumber = GameOperationCLI.getUserChoice(3, options);
            if (betChoiceNumber == 3){
                return;
            }
            int betAmountNumber = GameOperationCLI.getUserChoice(playerBankroll, "How Much?");
            switch (betChoiceNumber){
                case 1:
                    addToPlayerBets(new PassLine(betAmountNumber));
                    playerBankroll -= betAmountNumber;
                    break;
                case 2:
                    System.out.println("not yet supported");
                    //TODO addToPlayerBets(new DontPassLine(betAmountNumber));
                    break;
            }
        }
        else{
            String options = FILLER_LINE + "Make a Place Bet\n" +
                    "1 -- Place Bet on the 4\n" +
                    "2 -- Place Bet on the 5\n" +
                    "3 -- Place Bet on the 6\n" +
                    "4 -- Place Bet on the 8\n" +
                    "5 -- Place Bet on the 9\n" +
                    "6 -- Place Bet on the 10\n" +
                    "7 -- Previous menu";
            int betChoiceNumber = GameOperationCLI.getUserChoice(7, options);
            if (betChoiceNumber == 7){
                return;
            }
            int betAmountNumber = GameOperationCLI.getUserChoice(playerBankroll, "How Much?");
            playerBankroll -= betAmountNumber;
            int decisionValue = 0;
            switch (betChoiceNumber){
                case 1:
                    decisionValue = 4;
                    break;
                case 2:
                    decisionValue = 5;
                    break;
                case 3:
                    decisionValue = 6;
                    break;
                case 4:
                    decisionValue = 8;
                    break;
                case 5:
                    decisionValue = 9;
                    break;
                case 6:
                    decisionValue = 10;
                    break;
            }
            Bet createdBet = new PlaceBet(decisionValue, betAmountNumber);
            addToPlayerBets(createdBet);

        }

    }
    private void removeBet() {
        if(playerBets.isEmpty()){
            System.out.println("You have no bets to pull");
            return;
        }
        //these options viewed by player as index from 1, but processed as index of 0. Calculations included.
        System.out.println(FILLER_LINE);
        for (int i = 0; i < playerBets.size(); i++){
            Bet currentBet = playerBets.get(i);
            String currentBetSummary = " --- " + currentBet.getBetAmount() + " dollar " + currentBet.getBetName() + " ---";
            System.out.println((i + 1) + currentBetSummary );
        }
        int selectedIndex = GameOperationCLI.getUserChoice(playerBets.size(), FILLER_LINE + "Select a bet to pull") - 1;
        Bet selectedBet = playerBets.get(selectedIndex);
        int selectedBetBetAmount = selectedBet.getBetAmount();

        //if the bet is able to be pulled, remove from playerBets and refund bet amount
        if (selectedBet.pullBet()){
            playerBets.remove(selectedIndex);
            playerBankroll += selectedBetBetAmount;
        }

    }

    private void addToPlayerBets(Bet betToAdd){
        String newBetName = betToAdd.getBetName();
        for (Bet bet: playerBets){
            //if the bet already exists, just pressure the bet. Otherwise, add it to playerBets
            if (bet.getBetName().equals(newBetName)){
                bet.setBetAmount(bet.getBetAmount() + betToAdd.getBetAmount());
                return;
            }
        }
        playerBets.add(betToAdd);
    }


    private void listBets(){
        System.out.println("Here is a list of your bets\n \n");
        if (playerBets.size() == 0){
            System.out.println("You have no bets to display");
        }
        for(Bet bet : playerBets){
            String betSummary = "--- " + bet.getBetAmount() + " dollar " + bet.getBetName() + " ---";
            System.out.println(betSummary);
        }
    }

    public int getPoint() {
        return point;
    }
}
