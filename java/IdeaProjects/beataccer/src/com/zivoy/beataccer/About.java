package com.zivoy.beataccer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class About extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextPane aProgramMadeByTextPane;

    public About() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.setMinimumSize(new Dimension(300, 300));
        this.setSize(new Dimension(400, 400));

        this.getRootPane().registerKeyboardAction(e -> {
            dispose();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

        buttonOK.addActionListener(e -> onOK());
    }

    public static void main(String[] args) {
        About dialog = new About();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void onOK() {
        // add your code here
        dispose();
    }
}
