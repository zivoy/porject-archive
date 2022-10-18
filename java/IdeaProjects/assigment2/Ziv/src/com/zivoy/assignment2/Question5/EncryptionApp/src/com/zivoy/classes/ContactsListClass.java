package com.zivoy.classes;

import com.zivoy.keyHandlers.Key;
import com.zivoy.keyHandlers.PrivateKey;

import javax.swing.*;

// class that handles the contact list
public class ContactsListClass {
    // variables
    private JList<Element> list;
    private DefaultListModel<Element> model;
    private Element myPrivate;

    // constructor
    public ContactsListClass(JList<Element> list, PrivateKey mykey) {
        this.list = list;  // a JList instance
        this.model = (DefaultListModel<Element>) list.getModel(); // the model of the JList
        this.myPrivate = new Element("Me - Decrypt", mykey, this.model.size()); // a private key element
        this.appendElement(myPrivate); // adding the private key as the first element in list
        list.setSelectedIndex(0);
    }

    // function for updating the private key
    public void updatePrivateKey(PrivateKey key) {
        Element newPrivate = new Element("Me - Decrypt", key, 0);
        this.model.setElementAt(newPrivate, 0);
        this.myPrivate = newPrivate;
        //this.list.setModel(this.model);
    }

    // append an element + an overhauling
    void appendElement(String string, Key value) {
        this.appendElement(new Element(string, value, this.model.size()));
    }

    void appendElement(Element value) {
        this.model.add(this.model.size(), value);
    }

    // get the key in the element
    public Key getSelectedKey() {
        return this.list.getSelectedValue().getKeyValue();
    }

    // check that something is selected
    public boolean isSelected() {
        return !this.list.isSelectionEmpty();
    }

    // get the model on the list and remove the private key
    public DefaultListModel<Element> getModel() {
        DefaultListModel<Element> model = new DefaultListModel<>();  // make a new model list
        for (int i = 0; i < this.model.getSize(); i++) {
            model.addElement(this.model.elementAt(i));   // copy all th items into it
        }

        model.removeElement(this.myPrivate); // remove the private key element
        return model;  // return
    }

    // set a model as the list model and add the private key as the first element
    public void setModel(DefaultListModel<Element> model) {
        model.add(0, this.myPrivate); // add the private key as the first element
        this.model = model;       // set the model as the model
        this.list.setModel(model);
    }
}
