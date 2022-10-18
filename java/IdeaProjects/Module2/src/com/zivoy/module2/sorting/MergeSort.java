package com.zivoy.module2.sorting;

import java.util.List;

public class MergeSort<T> extends Sort<T> {
    public MergeSort(List<T> inputList) {
        super(inputList, "Merge Sort");
    }

    public MergeSort() {
        super("Merge Sort");
    }

    @Override
    public SortResult sort() {
        loopsExecuted = 0;
        comparisonsMade = 0;
        valuesShifted = 0;
        long startTime = System.currentTimeMillis();

        sort(itemList);

        timeTook = System.currentTimeMillis() - startTime;
        return new SortResult(sortName, itemList, loopsExecuted,
                comparisonsMade, valuesShifted, timeTook);
    }

    void sort(List<T> list) {
        int length = list.size();
        if (length < 2) {
            return;
        }

        int mid = length / 2;
        List<T> left = subList(list, 0, mid);
        List<T> right = subList(list, mid, length);

        sort(left);
        sort(right);

        merge(list, left, right);
    }

    private void merge(List<T> a, List<T> left, List<T> right) {
        int sNum = left.size();
        int eNum = right.size();
        int sCurr = 0, eCurr = 0;

        for (int i = 0; i < sNum + eNum; i++) {
            loopsExecuted++;
            T val;
            if (sCurr < left.size() && eCurr < right.size()) {
                T sVal = left.get(sCurr);
                T eVal = right.get(eCurr);
                if (compare(sVal, eVal) <= 0) {
                    val = sVal;
                    sCurr++;

                } else {
                    val = eVal;
                    eCurr++;
                }

            } else {
                if (sCurr < left.size()) {
                    val = left.get(sCurr);
                    sCurr++;
                } else {
                    val = right.get(eCurr);
                    eCurr++;
                }
            }

            valuesShifted++;
            a.set(i, val);
        }
    }
}
