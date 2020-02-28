package com.zivoy.assignment2;

import java.util.Random;
import java.util.Scanner;

/**
 * @author Ziv Shalit
 * @Assignment 2
 * @Question 1
 * @Date 2020/02/28
 */

public class Question1 {
    public static void main(String[] args) {
        // make scanner and random objects for making random numbers and taking input
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        // explain the game to the user briefly     i dont think i translated this well its the song you do before rock paper scissors
        System.out.println("(0)Rock, (1)Paper, (2)Scissors, lets find the winner between the 2, on 3:");

        // boolean variable for playing again
        boolean next;
        do {
            // play game
            play(random, scanner);

            // ask if you want to play again
            System.out.print("Do you wish to play again? ");
            // get if they want to play
            next = confirm(scanner.next());
            // line spacer
            System.out.println();
        } while (next); // loop until they say to stop
    }

    // function to confirm if they wish to play
    private static boolean confirm(String input){
        // if the input is true, yes, y or 1 it will return true otherwise false
        switch (input.toLowerCase()){
            case "true":
            case "yes":
            case "y":
            case "1":
                return true;
            default:
                return false;
        }
    }

    // function that plays the game
    private static void play(Random random, Scanner scanner){
        // computer chooses a number
        int computer = random.nextInt(3);

        // create a variable for the user input and set it so that it will make it loop again if the user dident return anything
        int userInput=-1;
        // create a prompt for the use
        String prompt = "Your hand: ";

        // start loop
        do {
            // prompt the user
            System.out.print(prompt);

            // take input if its an integer
            if (scanner.hasNextInt()) userInput=scanner.nextInt();
            // look for the next line
            else scanner.next();

            // change the prompt to what should be entered doesnt matter if they did the right input
            prompt = "Your hand [0,1,2]: ";
        } while (!(0<=userInput && userInput<=2));  // loop until its a good input

        // countdown and wait 1 second between each number
        System.out.println(1);
        sleep(1000);
        System.out.println(2);
        sleep(1000);
        System.out.println(3);


        // spacers and declaring what each player threw
        System.out.println();
        System.out.println("You throw " + nameItem(userInput));
        System.out.println("The computer threw " + nameItem(computer));
        System.out.println();

        // check if it was a tie
        if (userInput == computer) {
            System.out.println("TIE!");
            return;
        }
        // check who won and print appropriate message
        if (determineWinner(userInput, computer))
            System.out.println("You won!");
        else System.out.println("The computer won!");
    }

    // function that converts an int to a string
    private static String nameItem(int item){
        switch (item){
            case 0:
                return "Rock";
            case 1:
                return "Paper";
            case 2:
                return "Scissors";
            default:
                return "NON VALID INTEGER";
        }
    }

    // functions for which number beats which
    private static boolean determineWinner(int you, int opponent){
        switch (you){
            case 0:  // if rock
                if (opponent == 2)  // if opponent scissors
                    return true;  // you won
                break;
            case 1: // if paper
                if (opponent == 0)  // if opponent rock
                    return true;  // you won
                break;
            case 2: // if scissors
                if (opponent == 1)  // if opponent paper
                    return true;  // you won
                break;
        }
        return false;  // you lost if nothing was returned
    }

    // function that sleeps exits if interrupted
    private static void sleep(long time){
        try{
            Thread.sleep(time);
        } catch (InterruptedException ignored){ System.exit(0);}
    }
}
