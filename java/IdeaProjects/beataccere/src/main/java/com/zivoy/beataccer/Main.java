package com.zivoy.beataccer;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new ApplicationWindow("Beat Saber low acc playlist creator");
            int a =7 ;
            frame.setVisible(true);
        });
    }
}
