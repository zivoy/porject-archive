package com.zivoy;

// imports
import com.zivoy.windows.ApplicationWindow;

import javax.swing.*;

/**
 * @author Ziv Shalit
 * @Assignment 2
 * @Question 5
 * @Date 2020/02/24
 */

// main class that runs everything
public class Main {
    // starting class
    public static void main(String[] args) {
        // run main window in a threadsafe manner
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new ApplicationWindow("Encryption app");
            frame.setVisible(true);
        });
    }
}
