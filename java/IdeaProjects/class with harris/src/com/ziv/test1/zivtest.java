package com.ziv.test1;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * @author Ziv Shalit
 * @date 2020/03/04
 * @problom test 1 - programming part
 * @description
 */

public class zivtest {
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
        // set up frame
        JFrame frame = new JFrame("Craps test");
        // roll result field
        JTextField result = new JTextField();

        // roll button
        JButton roll = new JButton("roll dice");
        // make button fill area
        roll.setContentAreaFilled(true);

        // inner panes for centering content
        JPanel inner = new JPanel();
        JPanel innertop = new JPanel();

        // instructions label for explaining what to do
        JLabel instructions = new JLabel("you must get");

        // panels for content sections
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();

        // make a layout
        frame.setLayout(new GridLayout(3, 1));
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));

        // center the instructions label
        innertop.add(instructions);
        // add it to top panel
        panel1.add(innertop);

        // add the result text field to middle panel
        panel2.add(result);
        // male it uneditable
        result.setEditable(false);

        // center the roll button
        inner.add(roll);
        // add it to bottom field
        panel3.add(inner);

        // default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add panels to frame
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);

        // make dice collection with 2 dice
        DieCollection dices = new DieCollection(2 );
        // make game object
        Game game = new Game(instructions,result,dices);

        // create a button listener to roll dice
        roll.addActionListener(e -> game.roll());

        // pack window
        frame.pack();
        // make it visible
        frame.setVisible(true);
    }

    // win screen
    private static void winScreen(Game game){
        // show dialog telling user that they won
        int dialogResult = JOptionPane.showConfirmDialog (null,
                "You have won!\n" +
                        "do you wish to play again?",
                "You won!", JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.NO_OPTION){
            // if they say no to playing again then show dialog again
            winScreen(game);
        }
        // restart the game otherwise
        game.restart();
    }

    // loose screen
    private static void looseScreen(Game game){
        // show dialog telling user that they lost
        int dialogResult = JOptionPane.showConfirmDialog (null,
                "You have lost :(\n" +
                        "do you wish to play again?",
                "You lost.", JOptionPane.YES_NO_OPTION);
        // if they say no to playing again
        if(dialogResult == JOptionPane.NO_OPTION){
            // sow it again
            looseScreen(game);
        }
        // restart the game otherwise
        game.restart();
    }

    // game class
    static class Game {
        // this value keeps the round
        int round;
        // this keeps the instructions label
        JLabel instructions;
        // this text field is for showing the sum
        JTextField results;
        // this is for keeping the dice
        DieCollection dice;
        // this is the default start message ------- newline is not working in label
        final String string1 = "You must get a 7 or 11 to win. \n2, 3 and 12 will make you loose";
        // variable for second string
        String string2;
        // goal that they have to roll to later
        int goal;

        // constructor
        Game(JLabel instructions, JTextField results, DieCollection die) {
            // set the round to the first one
            this.round = 0;
            // save variables
            this.instructions = instructions;
            this.results = results;
            this.dice = die;

            // roll the dice
            this.dice.roll();
            this.results.setText(this.dice.toString());
            this.instructions.setText(string1);
        }

        private void roll() {
            // roll dice
            dice.roll();
            // set text in field
            this.results.setText(this.dice.toString());
            // evaluate game
            this.evaluate();
        }

        // evaluate round
        private void evaluate() {
            switch (this.round) {
                // if its first round
                case 0:
                    // the evaluate with round one rules
                    switch (roundOne()){
                        // they won
                        case 0:
                            winScreen(this);
                            break;
                        // they lost
                        case 1:
                            looseScreen(this);
                            break;
                        // they moved to the second round
                        case 2:
                            // switch to second round
                            this.round = 1;
                            // make new string
                            string2 = "You must get " + this.dice.sum + " to win.\n7 will make you loose";
                            // set text
                            this.instructions.setText(string2);
                            // set goal
                            this.goal=this.dice.sum;
                            break;
                    }
                    break;
                // second round
                case 1:
                    // evaluate with round 2 rules
                    switch(this.gameRound()){
                        // they won
                        case 0:
                            winScreen(this);
                            break;
                        // they lost
                        case 1:
                            looseScreen(this);
                            break;
                    }
                    // otherwise do nothing
                    break;
            }
        }

        // restart game
        private void restart() {
            // set to round 1
            this.round=0;
            // set instruction text to the first one
            this.instructions.setText(string1);
            // roll dice
            this.roll();
        }

        // round one rules
        private int roundOne() {
            switch (this.dice.sum) {  // check the result
                case 7:  // neutral
                case 11:
                    return 0;
                case 2:
                case 3:  // craps
                case 12:
                    return 1;
                default: // otherwise set new goal and start function
                    return 2;
            }
        }

        private int gameRound() {
            if (this.dice.sum == 7) {  // loose condition
                return 1;
            } else if (this.dice.sum == goal) { // win condition
                return 0;
            }
            return 2;
        }
    }
}
