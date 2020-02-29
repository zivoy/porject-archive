package com.zivoy.assignment2.Question3;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Question3 {
    // die collection class
    static class DieCollection{
        Random random = new Random(); // random for generating randoms

        // die class for handling dice
        class Dice{
            int rolled; // the current rolled number

            // constructor
            Dice(){
                this.roll(); // roll the dice
            }

            // function that rolls the dice
            void roll(){
                this.rolled= random.nextInt(6)+1; // generates a random number that is 0<=n<=6
            }
        }

        // variables for die collection
        Dice[] dies;  // the dies
        public int sum; // the sum of the dies
        public int numOfDies;  // the number of dice

        // constructor
        DieCollection(int numOfDies){
            this.numOfDies = numOfDies; // set the number of dice
            this.dies = new Dice[numOfDies]; // set the size of the dice array

            // fill it with dice
            for (int i=0;i<numOfDies;i++){
                this.dies[i] = new Dice();
            }

            // sum up the dice
            this.sumUp();
        }

        // get the number of die at index
        public int getDie(int dieNum) {
            return this.dies[dieNum].rolled; // get the value of this die
        }

        // sums up all dies
        private void sumUp(){
            // reset the sum
            this.sum=0;

            // iterate over all the dice
            for (Dice i: this.dies){
                this.sum+=i.rolled;  // and add it to the sum
            }
        }

        // roll all the dice
        public void roll(){
            // iterate over all the dice and roll them
            for (Dice i: this.dies){
                i.roll();
            }
            // sum up the dice
            this.sumUp();
        }

        // override the get string to return all the dice and the sum
        @Override
        public String toString() {
            // create a StringBuilder
            StringBuilder string = new StringBuilder();

            // iterate over all the dice
            for (int i=0; i<this.dies.length; i++){
                // add the dices value to the string
                string.append(this.getDie(i));
                // add a separator
                string.append(" ");

                // if its not the last value
                if (i!=this.dies.length-1){
                    // add a plus and separator
                    string.append("+ ");
                }
            }
            // add an equals and separator
            string.append("= ");
            // add the sum to the string
            string.append(this.sum);

            // return the string
            return string.toString();
        }
    }

    public static void main(String[] args) {
        DieCollection dices = new DieCollection(2 ); // create dice
        System.out.println("We will now be playing Craps\n" +
                           "2 6 sided dice will be rolled\n" + // explain the rules
                           "On the first roll if the sum of dice is 7 or 11 you will win\n" +
                           "But if its 2, 3 or 12 you will lose.\n" +
                           "Otherwise the sum will be used as the rolling goal\n" +
                           "You will continue to reroll until you get that number or a 7\n" +
                           "If you rolled a 7 then you lose\n" +
                           "But if you get the number then you win");
        System.out.println(); // blank line

        Scanner scanner = new Scanner(System.in);  // create scanner
        boolean again;  // make repeat variable false by default
        do {
            game(dices);  // start game
            System.out.print("\nDo you wish to play again? "); // ask if user wants to play again
            again=confirm(scanner.next()); // confirm it
            System.out.println(); // blank line
        } while (again);  // repeat if they said yes
    }

    // function for waiting till the user pressed enter ignore errors from entering characters
    public static void waitTillEnter(){
        try{
            int ignore =System.in.read(); // wait for enter
        } catch (IOException ignored){} // ignore errors
    }

    // main game function
    public static void game(DieCollection dice)  {
        System.out.print("Press enter to roll");  // prompt user to roll
        waitTillEnter(); // wait till they press enter
        System.out.println("You rolled " + dice);  // tell them what they rolled
        switch (dice.sum){  // check the result
            case 7:  // neutral
            case 11:
                System.out.println("You win!"); // they won
                break;
            case 2:
            case 3:  // craps
            case 12:
                System.out.println("You lose."); // they lost
                break;
            default: // otherwise set new goal and start function
                System.out.println("You now have to try and get " + dice.sum + " to win"); // tell them their new goal
                rollToGoal(dice, dice.sum);  // start the loop to roll til that goal
                break;
        }
    }

    // recursive function till you win or lose
    private static void rollToGoal(DieCollection dice, final int goal) {
        System.out.println();  // blank line
        System.out.print("Press enter to roll"); // prompt to roll
        waitTillEnter(); // wait till they roll
        dice.roll(); // roll the dice
        System.out.println("You rolled " + dice); // tell them what they rolled
        if (dice.sum == 7) {  // loose condition
            System.out.println("You lose.");
        } else if (dice.sum == goal) { // win condition
            System.out.println("You win!");
        } else{  // roll again till win or loose
                rollToGoal(dice, goal);
        }
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
}
