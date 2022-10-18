package com.zivoy.module5;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/**
 * implementation of A000045  // the Fibonacci sequence
 *
 * @author: Ziv
 */

public class Main {
    public static void main(String[] args) {
        Fibonacci fib = new Fibonacci();
        Scanner scanner = new Scanner(System.in);

        // request big value to load cache for faster results
//        fib.getNthElement(5000);

        System.out.println("Fibonacci number giver -- gets number at given index");
        System.out.println("type stop to stop");


        do {
            // prompt
            System.out.print("Fib: ");
            String input = scanner.nextLine();

            // handle user
            if (input.toLowerCase().equals("stop")) {
                break;
            }

            // get integer
            int index;
            try {
                index = Integer.parseInt(input);
            } catch (Exception ignored) {
                System.out.println("Bad input!");
                continue;
            }

            // limit user
            if (index < 0) {
                System.out.println("Number too small");
                continue;
            }
            if (index > 99999) {
                System.out.println("Number is too big");
                continue;
            }

            // get value
            BigInteger value;
            try {
                value = fib.getNthElement(index);
            } catch (OutOfMemoryError ignored) {
                System.out.println("The program ran out of ram clearing cache");
                fib.clearCache();
                continue;
            }
            // print output
            System.out.printf("%,d\n", value);
        } while (true);
        System.out.println("bye bye!");
    }
}

// fibonacci class
class Fibonacci {
    // cache hashmap
    private final Map<Integer, BigInteger> cache;

    // constructor
    public Fibonacci() {
        // make the cache
        cache = new HashMap<>();
        // and clear/fill it
        clearCache();
    }

    public BigInteger getNthElement(int elementIndex) {
        // element to load up queue in case of elements over 10000
        if (elementIndex >= 10000) {
            Stack<Integer> stack = new Stack<>();
            // load a starter number
            getElement(5000);
            // generate a stack
            for (int i = elementIndex; i > 9999; i -= 9999) {
                stack.add(i);
            }
            // and load it
            while (!stack.isEmpty()) {
                getElement(stack.pop());
            }
        }
        // now return number
        return getElement(elementIndex);
    }


    // recursive function for getting the nth fib number
    private BigInteger getElement(int elementIndex) {
        // if element is in cache     ....      then return it
        if (cache.containsKey(elementIndex)) return cache.get(elementIndex);

        // otherwise calculate the previous element plus the 2 elements before that
        BigInteger element1 = getElement(elementIndex - 2);
        BigInteger element2 = getElement(elementIndex - 1);
        BigInteger val = element1.add(element2);

        // add it to the cache and return it
        cache.put(elementIndex, val);
        return val;
    }

    // setup / clear cache
    public void clearCache() {
        // clear all entries
        cache.clear();
        // add the first 2 elements of the series
        cache.put(0, BigInteger.ZERO);
        cache.put(1, BigInteger.ONE);
    }
}
