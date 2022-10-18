package com.zivoy.module2.sorting;

import com.zivoy.module2.Book;

import java.util.ArrayList;
import java.util.List;

public abstract class Sort<T> {
    long loopsExecuted;
    long comparisonsMade;
    long valuesShifted;
    long timeTook;
    String sortName;
    List<T> itemList;

    public Sort(List<T> inputList, String sortName) {
        this.itemList = inputList;
        loopsExecuted = 0;
        comparisonsMade = 0;
        valuesShifted = 0;
        timeTook = 0;
        this.sortName = sortName;
    }

    public Sort(String sortName) {
        this(new ArrayList<>(), sortName);
    }

    public void copyToItemList(List<T> inputList) {
        this.itemList = subList(inputList, 0, inputList.size());
    }

    int compare(int item1, int item2) {
        comparisonsMade++;
        return Integer.compare(item1, item2);
    }

    int compare(long item1, long item2) {
        comparisonsMade++;
        return Long.compare(item1, item2);
    }

    int compare(String item1, String item2) {
        if (item1.equals(item2)) return 0;
        comparisonsMade++;
        int smallerLength = Math.min(item1.length(), item2.length());

        for (int i = 0; i < smallerLength; i++) {
            comparisonsMade++;
            loopsExecuted++;
            int car1 = item1.charAt(i);
            int car2 = item2.charAt(i);
            if (car1 == car2)
                continue;
            return 1;//first is bigger
        }
        comparisonsMade++;
        if (smallerLength == item2.length()) return -1;
        return 1;
    }

    int compare(Book item1, Book item2) {
        return compare(item1.getId(), item2.getId());
    }

    int compare(T item1, T item2) {
        if (!item1.getClass().equals(item2.getClass())) return 2;//not same type change this later

        if (item1 instanceof Integer) return compare((Integer) item1, (Integer) item2);
        if (item1 instanceof Long) return compare((Long) item1, (Long) item2);
        if (item1 instanceof String) return compare((String) item1, (String) item2);
        if (item1 instanceof Book) return compare((Book) item1, (Book) item2);

        return 2; //unsupported type
    }

    // returns index of smallest
    int min(int start, int end) {
        T smallest = itemList.get(start);
        int index = start;
        for (int i = end; i >= start; i--) {
            loopsExecuted++;
            T curr = itemList.get(i);
            if (compare(curr, smallest) < 1) {
                index = i;
                smallest = curr;
            }
        }
        return index;
    }

    int min(int start) {
        return min(start, itemList.size() - 1);
    }

    // -1 is sorted index otherwise
    int sortedUntil(int start) {
        for (int i = start; i < itemList.size() - 1; i++) {
            loopsExecuted++;
            comparisonsMade++;
            if (compare(itemList.get(i), itemList.get(i + 1)) > 0) return i + 1;
        }
        return -1;
    }

    int sortedUntil() {
        return sortedUntil(0);
    }

    boolean isSorted() {
        return sortedUntil() == -1;
    }

    void swap(int start, int index) {
        T curr = itemList.get(start);
        valuesShifted += 2;
        itemList.set(start, itemList.get(index));
        itemList.set(index, curr);
    }

    List<T> subList(List<T> inputArray, int start, int end) {
        List<T> sub = new ArrayList<>();
        for (int i = start; i < end; i++) {
//            loopsExecuted++;
            sub.add(inputArray.get(i));
        }
        return sub;
    }

    List<T> subList(int start, int end) {
        return subList(itemList, start, end);
    }

    public SortResult reverse() {
        long start = System.currentTimeMillis();
        int sCur = 0;
        int eCur = itemList.size() - 1;
        while (sCur < eCur) {
            loopsExecuted++;
            swap(sCur, eCur);
            sCur++;
            eCur--;
        }
        timeTook += System.currentTimeMillis() - start;
        return new SortResult(sortName, itemList, loopsExecuted,
                comparisonsMade, valuesShifted, timeTook);
    }

    public SortResult sortInfo() {
        return new SortResult(sortName, itemList, loopsExecuted,
                comparisonsMade, valuesShifted, timeTook);
    }

    public List<T> getItemList() {
        return itemList;
    }

    public void setItemList(List<T> inputList) {
        this.itemList = inputList;
    }

    abstract public SortResult sort();
}
