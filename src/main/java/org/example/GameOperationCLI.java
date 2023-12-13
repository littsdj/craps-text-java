package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameOperationCLI {
    public static final int MAX_BANK_ROLL = 1000000;
    private static final String WELCOME = "Welcome to my text based craps simulator!\n" +
            "All valid inputs will be positive integers.\n" +
            "At any time you can type 0 (zero) to exit the simulator.";
    private static final String INITIALIZE_BANKROLL = "Please enter a starting bankroll. (Max bankroll is " + MAX_BANK_ROLL + ")";
    public static void main(String[] args) {
        GameOperationCLI cli = new GameOperationCLI();
        cli.run();
    }
    private void run(){
        System.out.println(WELCOME);

            int bankroll = getUserChoice(MAX_BANK_ROLL, INITIALIZE_BANKROLL);
            CrapsTable table = new CrapsTable(bankroll);

            while (true){
                //prepare for comeout roll (puck off)
                table.prepareForRoll();

                //initiate comeout roll and handle come out rolls results
                table.nextRoll(0);

                while (table.getPoint() != 0){
                    //prepare for non-comeout roll (puck on)
                    table.prepareForRoll();

                    //handle all rolls that have a point
                    table.nextRoll(0);

                }
            }

    }

    public static int getUserChoice(int upperLimit, String prompt){

        while(true){
            Scanner userInput = new Scanner(System.in);
            System.out.println(prompt);
            try{
                int choice = userInput.nextInt();
                if (choice < 0){
                    throw new NegativeNumberException("Invalid Input --- Input integer is less than 0");
                }
                if (choice > upperLimit){
                    throw new ExceedsMaxException("Invalid Input --- Input integer is too large\nThe maximum acceptable amount is " + upperLimit);
                }
                if (choice == 0){
                    System.out.println("Thanks for playing!");
                    System.exit(0);
                    //throw new ZeroInputException("User input is zero");
                }
                return choice;
            } catch (InputMismatchException e){
                System.out.println("Unable to Read Input. Please enter an integer");
            } catch (ExceedsMaxException | NegativeNumberException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
