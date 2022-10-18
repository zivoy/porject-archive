package com.zivoy.module2;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new ProgramSelector("Select Program to Run");
            frame.setVisible(true);
        });
    }
}
