package com.zivoy.windows;

import com.zivoy.classes.Element;
import com.zivoy.keyHandlers.Key;
import com.zivoy.keyHandlers.PrivateKey;
import com.zivoy.keyHandlers.PublicKey;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

// dialog for editing contacts
public class EditContacts extends JDialog {
    // some components declared in form file -- intellij
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList<Element> list1;
    private JTextField keyFeild;
    private JTextField nameFeild;
    private JButton addContactButton;
    private JButton removeContactButton;
    private JTextField SelectedFeild;
    private JPanel SelctedPanel;
    private JCheckBox addPrivateCheckBox;
    private JLabel KeyTypeName;

    private DefaultListModel<Element> model;
    private ApplicationWindow.ContactsEditsTransfer onclose;
    private int selected = -1;


    // constructor
    public EditContacts(ApplicationWindow.ContactsEditsTransfer onclose) {
        this.model = onclose.model;  // set the model variable
        this.onclose = onclose; // ready thr onclose function

        list1.setModel(model); // set the list model to be the given model

        setContentPane(contentPane);  // set the main pane to be the main pane
        setModal(true); // set the modal to true
        SelctedPanel.setVisible(false); // make the selected item panel invisible
        setResizable(false); // make it not resizable
        //getRootPane().setDefaultButton(buttonOK);

        // add a listener to the ok button to run the ok function when clicked
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        // add a listener to the cancel button to run the cancel function when clicked
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

        // add a listener for when a element is selected
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!list1.isSelectionEmpty()) { // if the section is not empty
                    selected = list1.getSelectedIndex(); // set the selected element to the index
                    SelctedPanel.setVisible(true); // make the selected item panel visible
                    SelectedFeild.setText(list1.getSelectedValue().getKeyValue().getKey()); // get the current key and put it in the textbox
                } else {
                    SelctedPanel.setVisible(false); // otherwise make it invisible
                    list1.setSelectedIndex(0); // otherwise selected the first element
                    selected = 0;
                }
                if (model.size() == 0) selected = -1; // if there is nothing in it set the selected to -1
            }
        });

        // add a listener to the remove contact button
        removeContactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.size() > 0) {  // if there are elements in the list
                    selected --;  // set the selected index to be one lower
                    list1.setSelectedIndex(selected);  // set the selected item to the index
                } else {
                    selected = -1; // otherwise set it to -1
                }
                if (selected == -1) { // if selected is -1
                    if (list1.getModel().getSize() == 0) { // if the model has no elements then throw an error message
                        JOptionPane.showMessageDialog(null, "Select a contact first",
                                "Error -- no contact selected", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                model.removeElementAt(selected+1); // remove the element ad index

                sortModel(); // sort jList
                reindex(); // reindex all elements
            }
        });

        // action for listeners for input fields
        Action action = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameFeild.getText().equals("") || keyFeild.getText().equals("")) { // if both fields are empty throw error dialog
                    JOptionPane.showMessageDialog(null, "Values must be filled in",
                            "Error -- invalid values", JOptionPane.ERROR_MESSAGE);
                    return; //exit
                }
                // get value of name field
                String name = nameFeild.getText();
                boolean fail = false;
                Key key = null;
                if (!addPrivateCheckBox.isSelected()) { // check that the private key box is deselected
                    try {
                        key = PublicKey.fromString(keyFeild.getText()); // get public key
                    } catch (Exception E) {
                        fail = true; // error if error
                    }
                    if (fail || !((PublicKey) key).validate()) { // if there was an error or wor the key was invalid throw error message
                        JOptionPane.showMessageDialog(null, "I don't think that key is correct",
                                "Error -- invalid public key", JOptionPane.ERROR_MESSAGE);
                        return; // exot
                    }
                } else { // else the private key box is selected
                    try {
                        key = PrivateKey.fromString(keyFeild.getText()); // get private key
                    } catch (Exception E) { // throw an error dialog if erred
                        JOptionPane.showMessageDialog(null, "I don't think that key is correct",
                                "Error -- invalid public key", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                Element entery = new Element(name, key, model.size() + 1);  // make an element object
                model.addElement(entery); // add the element to list
                keyFeild.setText(""); // empty text fields
                nameFeild.setText("");
                sortModel(); // sort all elements
                reindex(); // reindex elements
            }
        };

        // add the listener to the name field key field and add contact button
        nameFeild.addActionListener(action);
        keyFeild.addActionListener(action);
        addContactButton.addActionListener(action);

        // add a listener to the private key checkbox
        addPrivateCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addPrivateCheckBox.isSelected()) { // if selcted
                    KeyTypeName.setText("Private Key:"); // set text to <-
                } else {
                    KeyTypeName.setText("Public Key:"); // set text to <-
                }
            }
        });
    }

    // on ok function
    private void onOK() {
        sortModel(); // sort all elements
        reindex(); // reindex elements
        this.onclose.close(this.model); // run the onclose function
        dispose(); // close the window
    }

    // on cancel function
    private void onCancel() {
        // close the window
        dispose();
    }

    // reindex elements
    private void reindex() {
        for (int i = 0; i < this.model.size(); i++) { // iterate over all elements
            Element curr = this.model.get(i); // get current element
            curr.index = i + 1; // set the index
            this.model.set(i, curr); // put it back in place
        }
    }

    // sort list model
    private void sortModel(){
        Element[] elements = new Element[this.model.size()]; // create array
        for (int i = 0; i < this.model.size(); i++) { // iterate over all elements
            elements[i] = this.model.get(i); // fill element array
        }
        sort(elements);// sort array
        for (int i = 0; i < elements.length; i++) { // iterate over all elements
            this.model.set(i,elements[i]);  // put element back in place
        }
    }

    // sort an element array
    private void sort(Element[] elements){
        do {
            for (int i = 0; i < elements.length + 1; i++) { // iterate over elements
                if (isSorted(getSliceOfArray(elements, 0, i))) // check if slice is sorted
                    continue;  // if so skip loop

                Element curr = elements[i - 1]; // otherwise get the element the made it out of order

                int start; // make a start position
                for (start=i-1;start>0;start--){ // go back from the position of where it is out of order to find a new place for it
                    if(!elements[start].name.equals(curr.name))
                        if (firstBiggerString(curr.name,elements[start].name)){
                            break;  // once found exit
                        }
                }//otherwise its 0

                for (int j=start; j < i; j++) {  // iterate over elements in that area and shift them
                    Element temp = elements[j]; // store what was in that place
                    elements[j] = curr; // replace it
                    curr = temp; // and move eh stored variable to be the next one to replace
                }
                break;  // break the loop
            }
        } while (!isSorted(elements)); // if its not sorted then do it again
    }

    // check if array is sorted
    private boolean isSorted(Element[] elements){
        for (int i=1; i<elements.length; i++){ // iterate over array
            if(!elements[i - 1].name.equals(elements[i].name)) // check that they are not the same
                if (firstBiggerString(elements[i-1].name, elements[i].name)) // if the first element is ever bigger then the next one
                return false; // then its not sorted
        }
        return true; // if it got to the end then it is sorted
    }

    // check which string is bigger
    private boolean firstBiggerString(String string1, String string2) {
        // determine the length of the shorted string
        int smallerLength = Math.min(string1.length(), string2.length());

        // iterate over all the characters
        for (int i = 0; i<smallerLength; i++) {
            int car1 = string1.charAt(i); // get the character in both strings at index
            int car2 = string2.charAt(i);
            if (car1 == car2) // if their the same skip this loop
                continue;
            return car1 > car2; // return if the character in string one is bigger then string 2
        }
        // if it passed through that loop then return if the smaller string was the second one that means that the first string is bigger
        return smallerLength == string2.length();
    }

    // get a slice of array
    public Element[] getSliceOfArray(Element[] arr, int start, int end) {
        // make a new array
        Element[] slice = new Element[end - start];
        // copy array into new array with slicing
        System.arraycopy(arr, start, slice, 0, slice.length);
        // return it
        return slice;
    }
}
