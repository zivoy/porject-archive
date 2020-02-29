package com.zivoy.windows;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import com.zivoy.classes.ContactsListClass;
import com.zivoy.classes.Element;
import com.zivoy.classes.FileManeger;
import com.zivoy.keyHandlers.Key;
import com.zivoy.keyHandlers.PrivateKey;
import com.zivoy.keyHandlers.PublicKey;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// main window
public class ApplicationWindow extends JFrame {
    // some components declared inform file -- intellij
    private JPanel mainPanel;
    private JList<Element> ContactsList;
    private JTextArea OutputTextArea;
    private JTextArea InputTextFeild;
    private JButton encryptDecryptButton;
    private JButton manageContactsButton;
    private JButton manageKeysButton;
    private JTextField PublicKeyFeild;
    private JScrollPane ConstactsScollFeild;
    private JScrollPane InputScrollArea;
    private JScrollPane OutputScrollArea;
    private JLabel PublicKeyLabel;
    private ContactsListClass contactList;

    // some variables
    private Key selected;
    private PrivateKey myPrivate;
    private PublicKey myPublic;
    private FileManeger contactSaver;

    // constructor
    public ApplicationWindow(String title) {
        super(title); // make the JFrame with this title

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set the default close operation to exit on close
        this.setContentPane(mainPanel); // set the panel to the main panel
        //this.setSize(600, 500); // set the size
        this.setResizable(false); // make it so you can resize
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                System.out.println(info.getName()); // get all theme names
                // if ("Nimbus".equals(info.getName())) { // check that the darcula theme exists
                UIManager.setLookAndFeel(new WindowsLookAndFeel());//DarculaLaf()); // set it to the windows look and feel
                //break;
                //}
            }
        } catch (UnsupportedLookAndFeelException e) {
            System.out.println("error");  // if there was an error then just print it
            e.printStackTrace();
        }
        // update the look and feel of everything
        SwingUtilities.updateComponentTreeUI(this);
        setup(); // run setup function
        this.pack(); // pack screen

        // add a listener to the encrypt/decrypt button
        encryptDecryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = InputTextFeild.getText(); // get the text in the input field
                if (text.equals("")); // if its nothing then do nothing
                else if (selected instanceof PrivateKey) { // else and the a private key is selected
                    text = ((PrivateKey) selected).decode(text); // set the text to be the decoded text
                } else if (selected instanceof PublicKey) { // if a public key is selected
                    text = ((PublicKey) selected).encode(text); // set the text to be the encoded text
                }
                OutputTextArea.setText(text); // set the output field to be text
            }
        });

        // add a listener for when a new element was selected in the contacts list
        ContactsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (contactList.isSelected()) { // if anything is selected
                    selected = contactList.getSelectedKey(); //set the selected variable to that key
                    updateButton(); // update the encrypt/decrypt button text
                } else {
                    ContactsList.setSelectedIndex(0); // else select the first element
                }
            }
        });

        // add a listener for when the manage contacts button is pressed
        manageContactsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                load(); // load the contacts from file
                JDialog contacts = new EditContacts(new ContactsEditsTransfer(contactList.getModel())); // make a new dialog
                contacts.pack(); // pack it
                contacts.setVisible(true); // and make it visible
            }
        });

        // add a listener for when the manage keys button is pressed
        manageKeysButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                load();  // load the contacts from file
                JDialog keyManeger = new ManageKeys(new ManageKeysTransfer(myPrivate, myPublic));  // make dialog
                keyManeger.pack(); // pack it
                keyManeger.setVisible(true);  //make it visible
            }
        });
    }

    // update the text on encrypt/decrypt button
    private void updateButton(Key selected) {
        if (selected instanceof PrivateKey) { // if its a private key
            encryptDecryptButton.setText("Decrypt Text"); // set the text to <-
        } else if (selected instanceof PublicKey) { // if its a public key
            encryptDecryptButton.setText("Encrypt Text"); // set the text to <-
        }
    }

    // same but uses' the selected contact
    private void updateButton() {
        updateButton(contactList.getSelectedKey());
    }

    // load contacts and keys from file
    private void load() {
        FileManeger.loadout items = contactSaver.load(); // load from file
        myPrivate = items.privateKey; // put private key in place
        myPublic = items.publicKey; // put public key in place
        contactList.setModel(items.model); // set the contacts to the new contacts list
    }

    // setup function
    private void setup() {
        contactSaver = new FileManeger("./contacts.txt");  // make the file manager object
        FileManeger.loadout items = contactSaver.load();  // load items
        myPrivate = items.privateKey; // set private key
        myPublic = items.publicKey; // set public key
        this.selected = myPrivate; // set selected as the private key
        updateButton(myPrivate); // update the button to the private key
        contactList = new ContactsListClass(ContactsList, myPrivate); // make the contacts list object
        PublicKeyFeild.setText(myPublic.getKey()); // put the public key in the text field
        contactList.setModel(items.model); // update teh contacts list
        contactSaver.save(myPrivate, myPublic, contactList.getModel()); // save everything back into the file
        if (!contactList.isSelected()) ContactsList.setSelectedIndex(0); // if nothing is selected select the first element
    }

    // class for maneging data sent and returned from the manege contacts dialog
    class ContactsEditsTransfer {
        public DefaultListModel<Element> model;

        // constructor put the model received in the variable
        public ContactsEditsTransfer(DefaultListModel<Element> model) {
            this.model = model;
        }

        // closing function
        public void close(DefaultListModel<Element> model) {
            contactList.setModel(model);  // set the model as the new model
            contactSaver.save(myPrivate, myPublic, contactList.getModel()); // save to file
        }
    }

    // class for maneging data send and returned from manege keys dialog
    class ManageKeysTransfer {
        public PublicKey publicKey;
        public PrivateKey privateKey;

        // constructor to put variables in place
        public ManageKeysTransfer(PrivateKey privateKey, PublicKey publicKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        // close function
        public void close(PrivateKey privateKey, PublicKey publicKey) {
            myPublic = publicKey; // update public key
            myPrivate = privateKey; // update private key
            PublicKeyFeild.setText(publicKey.getKey()); // update public key field
            contactList.updatePrivateKey(privateKey); // update the contact list
            if (!contactList.isSelected()) ContactsList.setSelectedIndex(0); // if nothing is selected select the first element
            selected = contactList.getSelectedKey(); // set the selected key
            contactSaver.save(myPrivate, myPublic, contactList.getModel()); // save to file
        }
    }
}