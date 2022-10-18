package com.zivoy.assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Random;

/**
 * @author Ziv Shalit
 * @Assignment 2
 * @Question 1
 * @Date 2020/02/28
 * <p>
 * rewrote with gui on 2020/03/05
 */

public class Question1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Rock paper scissors");

        // panels for content sections
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        // make a layout
        frame.setLayout(new GridLayout(2, 1));

        // label for text
        JLabel text = new JLabel();
        // add it to panel
        panel1.add(text);

        // create buttons for each option
        JButton rock = new JButton("Rock");
        JButton paper = new JButton("Paper");
        JButton scissors = new JButton("Scissors");

        // add them to the second panel
        panel2.add(rock);
        panel2.add(paper);
        panel2.add(scissors);

        // add the 2 panels to the frame
        frame.add(panel1);
        frame.add(panel2);

        // set the text on the label to set a default window size
        text.setText("Rock, Paper, Scissors, lets find the winner between the 2, on 3");
        // center the text
        text.setHorizontalAlignment(JTextField.CENTER);

        // set the default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // make random object for making random numbers
        Random random = new Random();

        // make the game object
        Game game = new Game(frame, random, text, panel2);

        // create listeners for each button that will evaluate the game
        rock.addActionListener(e -> game.round(0));
        paper.addActionListener(e -> game.round(1));
        scissors.addActionListener(e -> game.round(2));

        // pack window
        frame.pack();
        // show it
        frame.setVisible(true);
    }

    // main game class / contains logic
    static class Game {
        // keep the text label
        JLabel text;
        // keep the panel containing the buttons
        JPanel buttonPanel;
        // keep the frame
        JFrame frame;
        // keep the users last hand
        int hand = -1;

        // keep a random object
        Random random;

        // the default string to explain the game
        final String intro = "Rock, Paper, Scissors, lets find the winner between the 2";

        // constructor that puts stuff in place
        public Game(JFrame frame, Random random, JLabel textfield, JPanel buttons) {
            // puts values in variables
            this.text = textfield;
            this.buttonPanel = buttons;
            this.frame = frame;
            this.random = random;

            // make buttons visible
            this.buttonPanel.setVisible(true);
            // set the text on button
            this.text.setText(toLabel(this.intro + "\npick an option"));
        }

        // the function that handles game logic given the players hand
        public void round(int item) {
            // hide the buttons
            this.buttonPanel.setVisible(false);

            // keep the players hand
            this.hand = item;
            // computer chooses an option
            int computer = random.nextInt(3);

            // set the label to explain what happened
            this.text.setText(toLabel("You throw " + nameItem(this.hand) + "\n" +
                    "The computer threw " + nameItem(computer)));

            if (computer != this.hand) // check if tie
                if (determineWinner(computer)) // if not check if player won
                    // tell player they won
                    this.dialog("You won!", "You have won\nWould you like to play again?");
                    // otherwise tell player they lost
                else this.dialog("You lost.", "You have lost\nWould you like to play again?");
                // otherwise say it was a tie
            else this.dialog("TIE", "The game TIED\nWould you like to play again?");

            // show the buttons
            this.buttonPanel.setVisible(true);
            // change text back to explenation string
            this.text.setText(toLabel(this.intro + "\npick an option"));
        }

        // function for closing the window
        private void close() {
            this.frame.dispatchEvent(new WindowEvent(this.frame, WindowEvent.WINDOW_CLOSING));
        }

        // dialog function
        private void dialog(String title, String message) {
            // take the title and message and display it in a yes no dialog
            int dialogResult = JOptionPane.showConfirmDialog(null,
                    message, title, JOptionPane.YES_NO_OPTION);

            // if the user clicked no
            if (dialogResult == JOptionPane.NO_OPTION) {
                // close the app
                this.close();
            }
            // otherwise do nothing
        }

        // function that converts an int to a string
        private String nameItem(int item) {
            switch (item) {
                case 0: // rock
                    return "Rock";
                case 1: // paper
                    return "Paper";
                case 2: // scissors
                    return "Scissors";
                default: // otherwise
                    return "NON VALID INTEGER";
            }
        }

        // functions for which number beats which
        private boolean determineWinner(int opponent) {
            switch (this.hand) {
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
    }

    public static String toLabel(String string) {
        // add html tag to start and end
        string = "<html>" + string + "</html>";
        // replace and return newline character with break line html tag
        return string.replaceAll("\n", "<br/>");
    }
}
