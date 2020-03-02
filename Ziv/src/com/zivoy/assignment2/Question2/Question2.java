package com.zivoy.assignment2.Question2;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * @author Ziv Shalit
 * @Assignment 2
 * @Question 2
 * @Date 2020/02/28
 */

public class Question2 {
    public static void main(String[] args) {
        // create JFrame
        JFrame frame = new JFrame("ISBN validator");
        // create top panel
        JPanel panel = new JPanel();
        // create bottom panel
        JPanel panel2 = new JPanel();
        // create text area
        JTextField text = new JTextField();
        // inner panel for centering label
        JPanel inner = new JPanel();

        // add a border to panel
        int eb = 5;
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(eb, eb, eb, eb), null));

        // layout stuff
        frame.setLayout(new GridLayout(2, 1));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));

        // descriptive label
        panel.add(new JLabel("ISBN: "));
        // label for validation
        JLabel valid = new JLabel("This ISBN is invalid");


        // add text field to top panel
        panel.add(text);
        // add the validation label to the centering panel and add that to the second panel
        inner.add(valid);
        panel2.add(inner);

        // add panels to frame
        frame.add(panel);
        frame.add(panel2);

        // close on exit
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set default window size
        frame.setSize(500, 300);

        // make a listener
        text.getDocument().addDocumentListener(new DocumentListener() {
            // if the user adds anything removes anything or changes anything run the update function
            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            // when triggered
            public void update() {
                // make a confirm variable
                boolean confirm;
                // try to validate isbn
                try {
                    // put result in confirmed
                    confirm = validateIsbn(text.getText());
                } catch (Exception ignored) {
                    // if erred then its not valid
                    confirm = false;
                }

                // set the message to the appropriate one
                if (confirm)
                    valid.setText("This is a valid ISBN");
                else valid.setText("This ISBN is invalid");
            }
        });

        // pack frame and make it visable
        frame.pack();
        frame.setVisible(true);
    }

    // function that takes a character and returns an integer
    private static int charToInt(char input) {
        return Integer.parseInt(String.valueOf(input));
    }

    private static Boolean validateIsbn(String isbn) {
        // remove all dashes
        isbn = isbn.replace("-","");

        // this checker works for all lengths but for this assigment im limiting it only to 10 and 13 digit codes
        if (!(isbn.length() == 10 || isbn.length() == 13)){
            return false;
        }

        // turn the input to a char array
        char[] items = isbn.toCharArray();
        // declare a sum as 0
        int sum = 0;

        // iterate over all the digits except the checksum and add them to the running total
        for (int i = 0; i < items.length - 1; i++) {
            sum += charToInt(items[i]) * (i + 1);
        }

        // declare a checksum variable
        int checkSum;
        // get the last character
        char last = items[items.length - 1];
        // check if its a x therefore 10
        if (last == 'x' || last == 'X') checkSum = 10;
            // otherwise get the number
        else checkSum = charToInt(last);

        // check the the sum modulo 11 is the checksum and return it
        return (sum % 11) == checkSum;
    }
}
