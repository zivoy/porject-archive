package com.zivoy.module2;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ProgramSelector extends JFrame {
    String title;
    private JPanel mainPanel;
    private JButton buttonProgram1;
    private JButton buttonProgram2;
    private JButton buttonProgram3;
    private JButton buttonProgram4;

    public ProgramSelector(String title) {
        super(title);
        this.title = title;
        this.setContentPane(mainPanel);
        this.setMinimumSize(new Dimension(100, 250));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JFrame frame = this;

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (!frame.getTitle().equals(title)) {
                    frame.setTitle(title);
                    frame.setContentPane(mainPanel);
                    frame.setMinimumSize(new Dimension(100, 250));
                    frame.setSize(new Dimension(100, 250));
                    frame.revalidate();
                } else {
                    if (JOptionPane.showConfirmDialog(frame,
                            "Are you sure you want to close this window?", "Close Window?",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            }
        });

        buttonProgram1.addActionListener(actionEvent -> {
            new Program1(frame);
            frame.revalidate();
        });
        buttonProgram2.addActionListener(actionEvent -> {
            new Program2(frame);
            frame.revalidate();
        });
        buttonProgram3.addActionListener(actionEvent -> {
            ImageIcon icon = new ImageIcon(
                    Objects.requireNonNull(ProgramSelector.class.getClassLoader().getResource("dio.jpg")));
            JOptionPane.showMessageDialog(this,
                    "You expected Program 3,\n\nBut it was me, Program 4!", "HAH!",
                    JOptionPane.INFORMATION_MESSAGE, icon);
            new Program4(frame);
            frame.revalidate();
        });
        buttonProgram4.addActionListener(actionEvent -> {
            new Program4(frame);
            frame.revalidate();
        });
    }
}
