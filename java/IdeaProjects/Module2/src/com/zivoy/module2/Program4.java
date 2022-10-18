package com.zivoy.module2;

import com.zivoy.module2.sorting.MergeSort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class Program4 {
    ArrayList<Book> bookList;
    Search binary;
    Search serial;
    private JPanel mainPanel;
    private JButton button1;
    private JFormattedTextField titleTextField;
    private JFormattedTextField bTextField;
    private JFormattedTextField lTextField;
    private JSpinner spinner1;

    public Program4(JFrame frame) {
        frame.setTitle("Program 4 – ID Searcher");
        frame.setContentPane(mainPanel);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setSize(new Dimension(300, 300));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        bookList = new ArrayList<>();
        binary = new BinarySearch(bookList);
        serial = new LinearSearch(bookList);

        readBooks("Booklist.txt");

        spinner1.addChangeListener(e -> setSpinnerColor(Color.WHITE));

        button1.addActionListener(actionEvent -> search());

        ((JSpinner.DefaultEditor) spinner1.getEditor()).getTextField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    search();
                }
            }
        });
    }

    void readBooks(String resourceName) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(
                    Program4.class.getClassLoader().getResourceAsStream(resourceName))));

            String id;
            while ((id = reader.readLine()) != null) {
                String name = reader.readLine();
                if (name == null) break;

                bookList.add(new Book(Integer.parseInt(id), name));
            }

            reader.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        new MergeSort<>(bookList).sort();
        System.out.println(bookList.size() + " books loaded");
    }

    void search() {
        titleTextField.setValue("");

        int value = (int) spinner1.getValue();

        serial.search(value);
        if (binary.search(value).getIndex() == -1)
            setSpinnerColor(Color.decode("#FF9494"));
        else
            titleTextField.setValue(bookList.get(binary.getIndex()).getName());

        lTextField.setValue(serial.getComparisonsMade() + " Comparisons");
        bTextField.setValue(binary.getComparisonsMade() + " Comparisons");
    }

    void setSpinnerColor(Color color) {
        JSpinner.NumberEditor jsEditor = (JSpinner.NumberEditor)
                spinner1.getEditor();
        jsEditor.getTextField().setBackground(color);
    }

    interface Search {
        long comparisonsMade = 0;
        int itemIndex = -1;
        ArrayList<Book> array = new ArrayList<>();

        int getIndex();

        long getComparisonsMade();

        Search search(int value);

    }

    static class BinarySearch implements Search {
        long comparisonsMade;
        int itemIndex;
        ArrayList<Book> array;

        BinarySearch(ArrayList<Book> array) {
            comparisonsMade = 0;
            itemIndex = -1;
            this.array = array;
        }

        public int getIndex() {
            return itemIndex;
        }

        public long getComparisonsMade() {
            return comparisonsMade;
        }

        public BinarySearch search(int value) {
            comparisonsMade = 0;
            return search(value, 0, array.size() - 1);
        }

        BinarySearch search(int value, int left, int right) {
            if (right >= left) {
                int middle = left + (right - left) / 2;

                comparisonsMade++;
                if (array.get(middle).getId() == value) {
                    itemIndex = middle;
                    return this;
                }

                if (array.get(middle).getId() > value) return search(value, left, middle - 1);
                return search(value, middle + 1, right);
            }
            itemIndex = -1;
            return this;
        }
    }

    static class LinearSearch implements Search {
        long comparisonsMade;
        int itemIndex;
        ArrayList<Book> array;

        LinearSearch(ArrayList<Book> array) {
            comparisonsMade = 0;
            itemIndex = -1;
            this.array = array;
        }

        public int getIndex() {
            return itemIndex;
        }

        public long getComparisonsMade() {
            return comparisonsMade;
        }

        public LinearSearch search(int value) {
            comparisonsMade = 0;
            for (int i = 0; i < array.size(); i++) {
                comparisonsMade++;
                if (array.get(i).getId() == value) {
                    itemIndex = i;
                    return this;
                }
            }
            itemIndex = -1;
            return this;
        }
    }
}

