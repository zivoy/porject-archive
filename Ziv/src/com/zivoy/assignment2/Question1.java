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
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        System.out.println("(0)Rock, (1)Paper, (2)Scissors, lets find the winner between the 2, on 3:");
        boolean next;
        do {
            play(random, scanner);
            System.out.print("Do you wish to play again? ");
            next = confirm(scanner.next());
            System.out.println();
        } while (next);
    }

    private static boolean confirm(String input){
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

    private static void play(Random random, Scanner scanner){
        int computer = random.nextInt(3);

        int userInput=-1;
        String prompt = "Your hand: ";
        do {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                userInput=scanner.nextInt();
            }
            else {
                scanner.next();
                prompt = "Your hand [0,1,2]: ";
            }
        } while (!(0<=userInput && userInput<=2));

        System.out.println(1);
        sleep(1000);
        System.out.println(2);
        sleep(1000);
        System.out.println(3);


        System.out.println();
        System.out.println("You throw " + nameItem(userInput));
        System.out.println("The computer throw " + nameItem(computer));
        System.out.println();

        if (userInput == computer) {
            System.out.println("TIE!");
            return;
        }
        if (determineWinner(userInput, computer))
            System.out.println("You won!");
        else System.out.println("The computer won!");
    }

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

    private static boolean determineWinner(int you, int opponent){
        switch (you){
            case 0:
                if (opponent == 2)
                    return true;
                break;
            case 1:
                if (opponent == 0)
                    return true;
                break;
            case 2:
                if (opponent == 1)
                    return true;
                break;
        }
        return false;
    }

    private static void sleep(long time){
        try{
            Thread.sleep(time);
        } catch (InterruptedException ignored){}
    }
}
