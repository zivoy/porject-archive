package com.zivoy.module2.sorting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class sortTest {

    static int maximum = 7;
    static int minimum = -3;

    public static void main(String[] args) {
        long seed = System.currentTimeMillis();//-12;

        System.out.println(new SelectionSort<>(makeList(seed)).sort());
        System.out.println(new InsertSort<>(makeList(seed)).sort());
        System.out.println(new BubbleSort<>(makeList(seed)).sort());
        System.out.println(new MergeSort<>(makeList(seed)).sort());


        Sort<Integer> a = new SelectionSort<>(makeList(seed));
        a.sort();
        a.sortName = a.sortName + " - Reversed";
        printSort(a.reverse());
    }

    static List<Integer> makeList(long seed) {
        Random rand = new Random(seed);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) list.add(rand.nextInt(maximum - minimum + 1) + minimum);
        return list;
    }

    static void printSort(SortResult res) {
        System.out.println(res.sortName);
//        System.out.println(res.list);
        System.out.println("Number of times a comparison was made:\t\t" + res.comparisonsMade);
        System.out.println("Number of times the loop was executed:\t\t" + res.loopsExecuted);
        System.out.println("Number of times a value was shifted:\t\t" + res.millisecondCompletion);
        System.out.println("number of milliseconds to complete sort:\t" + res.valuesShifted);
        System.out.println();
    }
}
