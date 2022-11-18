package com.zivoy.classes;

import com.zivoy.keyHandlers.Key;

import java.util.Objects;

// Element class for list contains the key and a name
public class Element {
    // variables
    public String name;
    public int index;
    private Key keyValue;

    // constructor class
    public Element(String name, Key keyValue, int index) {
        this.name = name;     // contact name
        this.keyValue = keyValue; // contact key can be either public or private
        this.index = index;  // an index to separate it from other identical elements
    }

    // get the key of the element
    public Key getKeyValue() {
        return keyValue;
    }

    // override the equals function so object can be used as a key
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return index == element.index &&
                Objects.equals(name, element.name);
    }

    // override the hash function so object can be used as a key in a hashMap
    @Override
    public int hashCode() {
        return Objects.hash(name, index);
    }

    // override the toString function so that the name will display in the contact list
    @Override
    public String toString() {
        return name;
    }
}
