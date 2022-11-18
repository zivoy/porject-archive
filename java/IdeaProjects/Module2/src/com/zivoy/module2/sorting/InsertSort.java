package com.zivoy.module2.sorting;

import java.util.List;

public class InsertSort<T> extends Sort<T> {
    public InsertSort(List<T> inputList) {
        super(inputList, "Insertion Sort");
    }

    public InsertSort() {
        super("Insertion Sort");
    }

    private void shift(int start, int index) {
        T value = itemList.get(index);
        T curr;
        int i;
        for (i = start; i < index; i++) {
            loopsExecuted++;
            valuesShifted++;
            curr = itemList.get(i);
            itemList.set(i, value);
            value = curr;
        }
        valuesShifted++;
        itemList.set(i, value);
    }

    @Override
    public SortResult sort() {
        loopsExecuted = 0;
        comparisonsMade = 0;
        valuesShifted = 0;
        long startTime = System.currentTimeMillis();

        int startSearch = 0;

        while (true) {
            loopsExecuted++;
            int sortTill = sortedUntil(startSearch);

            if (sortTill == -1) break;

            T breakingVal = itemList.get(sortTill);
            int start = 0;
            for (int i = sortTill - 1; i >= 0; i--) {
                loopsExecuted++;
                if (compare(breakingVal, itemList.get(i)) <= 0) {
                    start = i;
                    break;
                }
            }
            startSearch = Math.max(start - 1, 0);
            shift(start, sortTill);
        }

        timeTook = System.currentTimeMillis() - startTime;
        return new SortResult(sortName, itemList, loopsExecuted,
                comparisonsMade, valuesShifted, timeTook);
    }
}
