package com.zivoy.beataccer;

import com.sun.org.apache.xpath.internal.operations.String;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Changelog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextPane v101FixedTextPane;

    public Changelog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        java.lang.String builder = "<html>\n" +
                "<b>v1.0.1</b>\n" +
                "<ul>\n" +
                "<li>Fixed bug couseed by having an integer amount of pp</li>\n" +
                "</ul>\n" +
                "<b>v1.0.0</b>\n" +
                "<ul>\n" +
                "<li>Made program</li>\n" +
                "</ul>\n" +
                "</html>";
        v101FixedTextPane.setText(builder);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onOK(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public static void main(String[] args) {
        Changelog dialog = new Changelog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void onOK() {
        // add your code here
        dispose();
    }
}
