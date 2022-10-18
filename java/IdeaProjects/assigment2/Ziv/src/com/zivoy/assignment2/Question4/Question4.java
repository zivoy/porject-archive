package com.zivoy.assignment2.Question4;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Ziv Shalit
 * @Assignment 2
 * @Question 4
 * @Date 2020/02/28
 */

public class Question4 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Array Question");  // make array question jFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set to close on close

        // make a monospaced font font
        Font font = new Font("Monospaced", Font.PLAIN, 12);
        // make a text area
        JTextArea display = new JTextArea();
        // set the font to the monospaced font
        display.setFont(font);
        // set the field to be not editable
        display.setEditable(false);

        // make a scroll pane with the text are in it
        JScrollPane scroll = new JScrollPane(display);

        // add it to the JPanel content panel
        frame.getContentPane().add(scroll);

        // set the preferred size
        scroll.setPreferredSize(new Dimension(600, 55));
        // disable resizability
        frame.setResizable(false);

        // make the random object for generating random numbers
        Random random = new Random();
        // make an arraylist of integers
        ArrayList<Integer> array = new ArrayList<>();

        // loop 100 times
        for (int i = 0; i < 100; i++)
            // add a random number that is 0<= n <= 20 to list
            array.add(random.nextInt(21));

        // make a array of 0's that has the size of the array list
        int[] newArray = new int[array.size()];
        // keep curr index
        int idx = 0;
        // iterate over all the items in the arraylist
        for (int i : array) {
            // if its not 0
            if (i != 0) {
                // add it to the array
                newArray[idx] = i;
                // and increase the index
                idx++;
            }
        }
        // the rest will automatically be 0's
        display.setText(" before: " + array + " \n" +
                " after:  " + Arrays.toString(newArray));
        // pack frame
        frame.pack();
        // make it visable
        frame.setVisible(true);
    }
}
