package com.zivoy.module5;

import java.math.BigInteger;

// class for testing fibonacci
public class Test {
    public static void main(String[] args) {
        // case 1
        System.out.println("Test 1 -- correct values");
        case1();

        System.out.println("\nvalues might be a bit wacky because of other processes on the system\n ");

        // case 2
        System.out.println("Test 2 -- time to evaluate");
        case2();

        // case 2
        System.out.println("Test 2 -- time to evaluate with clearing");
        case3();
    }

    // correct values
    static void case1() {
        Fibonacci fibonacci = new Fibonacci();
        try {
            //0
            equals(0, 0, fibonacci);
            fibonacci.clearCache();
            //2
            equals(2, 1, fibonacci);
            fibonacci.clearCache();
            //5
            equals(5, 5, fibonacci);
            fibonacci.clearCache();
            //7
            equals(7, 13, fibonacci);
            fibonacci.clearCache();
            //8
            equals(8, 21, fibonacci);
            fibonacci.clearCache();
            //20
            equals(20, 6765, fibonacci);
            fibonacci.clearCache();
            //199
            equals(199, new BigInteger("173402521172797813159685037284371942044301"), fibonacci);
        } catch (AssertionError e) {
            System.out.println("--- x Failed x ---");
            e.printStackTrace();
            return;
        }
        System.out.println("--- x Passed x ---");
    }

    // time to execute
    static void case2() {
        case2n3(false);
    }

    // time to execute with no previous caching
    static void case3() {
        case2n3(true);
    }

    // function for 2 and 3
    static void case2n3(boolean clearCache) {
        Fibonacci fibonacci = new Fibonacci();
        //8
        timeValue(8, fibonacci);
        if (clearCache) fibonacci.clearCache();
        //40
        timeValue(40, fibonacci);
        if (clearCache) fibonacci.clearCache();
        //70
        timeValue(70, fibonacci);
        if (clearCache) fibonacci.clearCache();
        //200
        timeValue(200, fibonacci);
        if (clearCache) fibonacci.clearCache();
        //150
        timeValue(150, fibonacci);
        if (clearCache) fibonacci.clearCache();
        //500
        timeValue(500, fibonacci);
        if (clearCache) fibonacci.clearCache();
        //1000
        timeValue(1000, fibonacci);
        System.out.println("--- x Passed x ---");
    }

    // is value as expected with long
    static void equals(int index, long expected, Fibonacci fibonacci) {
        equals(index, BigInteger.valueOf(expected), fibonacci);
    }

    // is value as expected
    static void equals(int index, BigInteger expected, Fibonacci fibonacci) {
        BigInteger value = fibonacci.getNthElement(index);
        if (!value.equals(expected)) {
            throw new AssertionError("at fib: " + index +
                    "\nexpected: " + expected.toString() +
                    "\ngot: " + value.toString());
        }
    }

    // run fib and time it
    static void timeValue(int index, Fibonacci fibonacci) {
        long start = System.nanoTime();
        fibonacci.getNthElement(index);
        long time = System.nanoTime() - start;
        System.out.printf("%d took: \t %d nanoseconds\n", index, time);
    }
}
