package com.zivoy.module2.sorting;

import java.util.List;

public class BubbleSort<T> extends Sort<T> {
    public BubbleSort(List<T> inputList) {
        super(inputList, "Bubble Sort");
    }

    public BubbleSort() {
        super("Bubble Sort");
    }

    @Override
    public SortResult sort() {
        loopsExecuted = 0;
        comparisonsMade = 0;
        valuesShifted = 0;
        long startTime = System.currentTimeMillis();

        boolean swapped;

        do {
            loopsExecuted++;
            swapped = false;
            for (int i = 0; i < itemList.size() - 1; i++) {
                loopsExecuted++;

                comparisonsMade++;
                if (compare(itemList.get(i), itemList.get(i + 1)) > 0) {
                    swap(i, i + 1);
                    swapped = true;
                }
            }

        } while (swapped);

        timeTook = System.currentTimeMillis() - startTime;
        return new SortResult(sortName, itemList, loopsExecuted,
                comparisonsMade, valuesShifted, timeTook);
    }
}
