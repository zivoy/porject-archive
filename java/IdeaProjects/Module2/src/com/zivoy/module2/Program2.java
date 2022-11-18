package com.zivoy.module2;

import com.zivoy.module2.sorting.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Program2 {
    boolean reversed;
    int numberOfElements;
    boolean updateSorted;
    Random rand;
    ConcurrentLinkedQueue<String> appendList;
    int canRun;
    int maximum = 10000;
    int minimum = -10000;
    private JTextArea outputTextArea;
    private JRadioButton a10RadioButton;
    private JRadioButton a100RadioButton;
    private JRadioButton a1000RadioButton;
    private JRadioButton a5000RadioButton;
    private JButton generateAndSortNumbersButton;
    private JRadioButton ascendingRadioButton;
    private JRadioButton descendingRadioButton;
    private JList<Integer> originalList;
    private JList<Integer> sortedList;
    private JPanel mainPanel;

    public Program2(JFrame frame) {
        frame.setTitle("Program 2 – Sorting Efficiencies");
        frame.setContentPane(mainPanel);
        frame.setMinimumSize(new Dimension(620, 650));
        frame.setSize(new Dimension(520, 550));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        rand = new Random();
        appendList = new ConcurrentLinkedQueue<>();
        canRun = 0;

        reversed = false;
        numberOfElements = 100;

        descendingRadioButton.addActionListener(actionEvent -> reversed = true);
        ascendingRadioButton.addActionListener(actionEvent -> reversed = false);

        a10RadioButton.addActionListener(actionEvent -> numberOfElements = 10);
        a100RadioButton.addActionListener(actionEvent -> numberOfElements = 100);
        a1000RadioButton.addActionListener(actionEvent -> numberOfElements = 1000);
        a5000RadioButton.addActionListener(actionEvent -> numberOfElements = 5000);

        generateAndSortNumbersButton.addActionListener(actionEvent -> {
            if (canRun > 0) return;
            outputTextArea.setText("");
            ArrayList<Integer> items = new ArrayList<>();

            for (int i = 0; i < numberOfElements; i++) {
                items.add(rand.nextInt(maximum - minimum + 1) + minimum);
            }

            updateSorted = true;

            canRun = 4;
            Thread[] sorts = new Thread[4];
            sorts[0] = new Thread(() -> sortAction(new MergeSort<>(), items));
            sorts[1] = new Thread(() -> sortAction(new InsertSort<>(), items));
            sorts[2] = new Thread(() -> sortAction(new BubbleSort<>(), items));
            sorts[3] = new Thread(() -> sortAction(new SelectionSort<>(), items));

            for (Thread i : sorts) i.start();

            DefaultListModel<Integer> unsorted = new DefaultListModel<>();

            for (Integer i : items) {
                unsorted.addElement(i);
            }

            originalList.setModel(unsorted);
            originalList.revalidate();

            for (Thread i : sorts) {
                try {
                    i.join();
                    while (!appendList.isEmpty())
                        outputTextArea.append(appendList.poll());
                    outputTextArea.revalidate();
                    canRun--;

                } catch (InterruptedException e) {
                    System.exit(-1);
                }
            }
        });
        frame.revalidate();
    }

    private void sortAction(Sort<Integer> sortSelection, ArrayList<Integer> items) {
        sortSelection.copyToItemList(items);
        sortSelection.sort();
        if (reversed) sortSelection.reverse();

        if (updateSorted) {
            updateSorted = false;
            DefaultListModel<Integer> sorted = new DefaultListModel<>();
            for (Integer i : sortSelection.getItemList()) {
                sorted.addElement(i);
            }
            sortedList.setModel(sorted);
            sortedList.revalidate();
        }

        appendList.add(sortSelection.sortInfo().toString() + "\n");
    }

    private void createUIComponents() {
        outputTextArea = new JTextArea();
        outputTextArea.setMargin(new Insets(5, 5, 5, 5));
    }
}
