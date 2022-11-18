package com.zivoy.beataccer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * child class of JTextField for creating text fields with prompt text
 * @author user3033626
 * with a bit of augmenting by me
 */
public class PTextField extends JTextField {

    String proptText;

    public PTextField(final String proptText) {
        super(proptText);
        this.proptText = proptText;

        addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(proptText);
                    setForeground(Color.GRAY);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                setForeground(Color.BLACK);
                if (getText().equals(proptText)) {
                    setText("");
                }
            }
        });
    }

    public void setPropt() {
        setText(proptText);
        setForeground(Color.GRAY);
    }

    public Boolean isEmpty() {
        return this.getText().equals("") || this.getText().equals(this.proptText);
    }

}