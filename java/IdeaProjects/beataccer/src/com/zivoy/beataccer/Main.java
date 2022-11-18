package com.zivoy.beataccer;

import javax.swing.*;

// todo once on github make version checker using api
// todo move text into resource bundles so it can be translated and centralized
// todo create error message dialog

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new ApplicationWindow("Beat Saber low acc playlist creator");
            frame.setVisible(true);
        });
    }
}
