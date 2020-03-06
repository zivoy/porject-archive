package com.zivoy.assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Ziv Shalit
 * @Assignment 2
 * @Question 1
 * @Date 2020/02/28
 *
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

        JLabel text = new JLabel();

        panel1.add(text);

        JButton rock = new JButton("Rock");
        JButton paper = new JButton("Paper");
        JButton scissors = new JButton("Scissors");


        panel2.add(rock);
        panel2.add(paper);
        panel2.add(scissors);

        frame.add(panel1);
        frame.add(panel2);

        text.setText("Rock, Paper, Scissors, lets find the winner between the 2, on 3");
        text.setHorizontalAlignment(JTextField.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




        // make scanner and random objects for making random numbers and taking input
        Random random = new Random();

        Game game = new Game(frame, random, text, panel2);

        rock.addActionListener(e -> game.round(0));
        paper.addActionListener(e -> game.round(1));
        scissors.addActionListener(e -> game.round(2));


        frame.pack();
        frame.setVisible(true);
    }


    static class Game extends Thread{
        JLabel text;
        JPanel buttonPanel;
        JFrame frame;
        int hand=-1;
        Random random;
        final String intro = "Rock, Paper, Scissors, lets find the winner between the 2";

        public Game(JFrame frame,Random random,JLabel textfield, JPanel buttons){
            this.text = textfield;
            this.buttonPanel = buttons;
            this.buttonPanel.setVisible(true);
            this.frame = frame;
            this.random = random;
            this.text.setText(toLabel(this.intro+"\npick an option"));
        }

        public void round(int item){
            this.buttonPanel.setVisible(false);
            this.hand = item;
            this.countDown();
            int computer = random.nextInt(3);
            this.text.setText(toLabel("You throw " + nameItem(this.hand)+"\n" +
                    "The computer threw " + nameItem(computer)));

            if (computer != this.hand)
                if (determineWinner(computer))
                    System.out.println("You won!");
                else System.out.println("You lost.");
             else System.out.println("TIE!");
             this.buttonPanel.setVisible(true);
            this.text.setText(toLabel(this.intro+"\npick an option"));
        }

        private void countDown(){
            // countdown and wait 1 second between each number
            this.text.setText(toLabel(this.intro+"\n1"));
            pause(1000);
            this.text.setText(toLabel(this.intro+"\n2"));
            pause(1000);
            this.text.setText(toLabel(this.intro+"\n3"));
        }

        // function that sleeps exits if interrupted
        private void pause(long time){
            this.frame.repaint();
            try {
                Thread.sleep(time);
            } catch (InterruptedException ignored){ this.close();}
            this.frame.repaint();
        }

        private void close(){
            this.frame.dispatchEvent(new WindowEvent(this.frame, WindowEvent.WINDOW_CLOSING));
        }

        // function that converts an int to a string
        private String nameItem(int item){
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
        private boolean determineWinner(int opponent){
            switch (this.hand){
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

    public static String toLabel(String string){
        string = "<html>"+string+"</html>";
        return string.replaceAll("\n","<br/>");
    }
}
