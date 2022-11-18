package com.zivoy.module2;

public class Book {
    int id;
    String name;

    Book(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}