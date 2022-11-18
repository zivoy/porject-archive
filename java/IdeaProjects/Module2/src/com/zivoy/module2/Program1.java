package com.zivoy.module2;

import com.zivoy.module2.sorting.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Program1 {

    boolean reversed;
    Random rand;
    int maximum = 1000;
    int minimum = -1000;
    private JPanel mainPanel;
    private JList<Integer> sortedList;
    private JList<Integer> originalList;
    private JRadioButton selectionRadioButton;
    private JRadioButton bubbleRadioButton;
    private JRadioButton insertRadioButton;
    private JRadioButton mergeRadioButton;
    private JRadioButton ascendingRadioButton;
    private JRadioButton descendingRadioButton;
    private JSpinner spinner1;
    private JButton generateAndSortNumbersButton;
    private JScrollPane scrollPane1;
    private JScrollPane scrollPane2;
    private Sort<Integer> selectedSort;

    public Program1(JFrame frame) {
        frame.setTitle("Program 1 – Sorting Routines");
        frame.setContentPane(mainPanel);
        frame.setMinimumSize(new Dimension(600, 600));
        frame.setSize(new Dimension(500, 500));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        rand = new Random();

        selectedSort = new SelectionSort<>();
        reversed = false;

        descendingRadioButton.addActionListener(actionEvent -> reversed = true);
        ascendingRadioButton.addActionListener(actionEvent -> reversed = false);

        selectionRadioButton.addActionListener(actionEvent -> selectedSort = new SelectionSort<>());
        bubbleRadioButton.addActionListener(actionEvent -> selectedSort = new BubbleSort<>());
        insertRadioButton.addActionListener(actionEvent -> selectedSort = new InsertSort<>());
        mergeRadioButton.addActionListener(actionEvent -> selectedSort = new MergeSort<>());

        generateAndSortNumbersButton.addActionListener(actionEvent -> {
            int numberOfItems = (int) spinner1.getValue();
            ArrayList<Integer> items = new ArrayList<>();

            for (int i = 0; i < numberOfItems; i++) {
                items.add(rand.nextInt(maximum - minimum + 1) + minimum);
            }
            selectedSort.copyToItemList(items);
            selectedSort.sort();
            if (reversed) selectedSort.reverse();

            ArrayList<Integer> sortedList = (ArrayList<Integer>) selectedSort.getItemList();

            DefaultListModel<Integer> unsorted = new DefaultListModel<>();
            DefaultListModel<Integer> sorted = new DefaultListModel<>();

            for (int i = 0; i < items.size(); i++) {
                unsorted.addElement(items.get(i));
                sorted.addElement(sortedList.get(i));
            }

            originalList.setModel(unsorted);
            this.sortedList.setModel(sorted);
        });
    }

    private void createUIComponents() {
        spinner1 = new JSpinner(new SpinnerNumberModel(25, 2, 50000, 1));
        scrollPane1 = new JScrollPane();
        scrollPane2 = new JScrollPane();
        BoundedRangeModel model = scrollPane1.getVerticalScrollBar().getModel();
        scrollPane2.getVerticalScrollBar().setModel(model);
        scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
    }
}
