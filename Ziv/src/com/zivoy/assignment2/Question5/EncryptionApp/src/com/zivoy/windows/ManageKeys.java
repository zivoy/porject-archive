package com.zivoy.windows;

import com.zivoy.keyHandlers.PrivateKey;
import com.zivoy.keyHandlers.PublicKey;

import javax.swing.*;
import java.awt.event.*;

// dialog for editing keys
public class ManageKeys extends JDialog {
    // some components declared in form file -- intellij
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField PublicKeyFeild;
    private JTextField PrivateKeyFeild;
    private JButton MakeRandomKeys;
    private JCheckBox editKeysCheckBox;

    private ApplicationWindow.ManageKeysTransfer onclose;
    PrivateKey currPrivate;
    PublicKey currPublic;

    // constructor
    public ManageKeys(ApplicationWindow.ManageKeysTransfer closeOperation) {
        setContentPane(contentPane); // set the main pane as the content pane
        this.onclose = closeOperation; // ready the onclose function
        currPrivate = closeOperation.privateKey; // set the private key
        currPublic = closeOperation.publicKey; // set the public key
        PrivateKeyFeild.setText(currPrivate.getKey()); // put it in a text field
        PublicKeyFeild.setText(currPublic.getKey()); // ^
        setModal(true); // set model to true

        setResizable(false); // make it not resizable
        getRootPane().setDefaultButton(buttonCancel); // select the cancel button bu default

        // create a listener for when the ok button is pressed run on ok function
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        // create a listener for when the cancel button is pressed run on cancel function
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // add listener for when the edit key checkbox is pressed
        editKeysCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean on = editKeysCheckBox.isSelected(); // get the sate
                PublicKeyFeild.setEditable(on); // set the edibility of fields to that state
                PrivateKeyFeild.setEditable(on); // ^
            }
        });

        // make a listener for when the make random keys button is pressed
        MakeRandomKeys.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrivateKey newPrivate = new PrivateKey(); // make a new private ket
                PublicKey newPublic = newPrivate.makePublic(); // make a new public key
                currPrivate = newPrivate; // set the private key
                currPublic = newPublic; // set the public key
                PublicKeyFeild.setText(newPublic.getKey()); // update text boxes
                PrivateKeyFeild.setText(newPrivate.getKey()); // ^
            }
        });
    }

    // on ok function
    private void onOK() {
        // give a warning that you have to give you contacts your new key
        int dialogResult = JOptionPane.showConfirmDialog (null,
                "Are you sure you would like to change your keys?\n" +
                        "You would have to send your new public key to your contacts!",
                "Warning", JOptionPane.OK_CANCEL_OPTION);
        if(dialogResult == JOptionPane.CANCEL_OPTION){
            return; // if they canceled  go back
        }
        currPrivate = PrivateKey.fromString(PrivateKeyFeild.getText());  // get keys
        currPublic = PublicKey.fromString(PublicKeyFeild.getText());
        String test = "Hello, World!";
        if (!currPrivate.decode(currPublic.encode(test)).equals(test)) { // validate keys as valid pair
            JOptionPane.showMessageDialog(null, "This is not is not a valid key pair",
                    "Error -- invalid key pair", JOptionPane.ERROR_MESSAGE); // error message if not
            return;
        }
        this.onclose.close(currPrivate, currPublic); // run close function
        dispose(); //close dialog
    }

    // on cancel
    private void onCancel() {
        // close dialog
        dispose();
    }
}
