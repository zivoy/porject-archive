package com.zivoy.module2.sorting;

import java.util.List;

public class SelectionSort<T> extends Sort<T> {
    public SelectionSort(List<T> inputList) {
        super(inputList, "Selection Sort");
    }

    public SelectionSort() {
        super("Selection Sort");
    }

    @Override
    public SortResult sort() {
        loopsExecuted = 0;
        comparisonsMade = 0;
        valuesShifted = 0;
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < itemList.size() - 1; i++) {
            loopsExecuted++;
            int smallest = min(i);
            comparisonsMade++;
            if (smallest == i) continue;
            swap(i, smallest);
        }

        timeTook = System.currentTimeMillis() - startTime;
        return new SortResult(sortName, itemList, loopsExecuted,
                comparisonsMade, valuesShifted, timeTook);
    }
}
