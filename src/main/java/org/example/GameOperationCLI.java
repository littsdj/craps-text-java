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
        try{
            getUserChoice(MAX_BANK_ROLL, INITIALIZE_BANKROLL);

        }catch (ZeroInputException e){
            System.out.println("Thanks for playing!");
        }
    }

    public static int getUserChoice(int upperLimit, String prompt) throws ZeroInputException{

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
                    throw new ZeroInputException("User input is zero");
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
