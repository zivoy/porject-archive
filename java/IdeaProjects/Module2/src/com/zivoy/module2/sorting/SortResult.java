package com.zivoy.module2.sorting;

import java.util.List;

public class SortResult {
    List<?> list;
    String sortName;
    long loopsExecuted;
    long comparisonsMade;
    long valuesShifted;
    long millisecondCompletion;

    public SortResult(String sortName, List<?> list, long loopsExecuted,
                      long comparisonsMade, long valuesShifted, long millisecondCompletion) {
        this.list = list;
        this.sortName = sortName;
        this.loopsExecuted = loopsExecuted;
        this.comparisonsMade = comparisonsMade;
        this.valuesShifted = valuesShifted;
        this.millisecondCompletion = millisecondCompletion;
    }

    public SortResult(List<?> list) {
        this("", list, -1, -1, -1, -1);
    }

    @Override
    public String toString() {
        return this.sortName + "\n" +
                "Number of times a comparison was made:\t" + this.comparisonsMade + "\n" +
                "Number of times the loop was executed:\t" + this.loopsExecuted + "\n" +
                "Number of times a value was shifted:\t" + this.valuesShifted + "\n" +
                "number of milliseconds to complete sort:\t" + this.millisecondCompletion + "\n";
    }
}
