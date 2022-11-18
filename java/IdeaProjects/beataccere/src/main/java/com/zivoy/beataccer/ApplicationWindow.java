package com.zivoy.beataccer;

import javax.swing.*;

public class ApplicationWindow extends JFrame{
    public JPanel mainPanel;
    private JPanel scoresaberUidPanel;
    private JPanel percentSelectorPanel;
    private JPanel generatePanel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JProgressBar progressBar1;
    private JSlider slider1;


    public ApplicationWindow(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this.setContentPane(mainPanel);

        this.pack();
    }
}
