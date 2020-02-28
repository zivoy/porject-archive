package com.zivoy.assignment2.Question2;

import java.util.Scanner;

/**
 * @author Ziv Shalit
 * @Assignment 2
 * @Question 2
 * @Date 2020/02/28
 */

public class Question2 {
    public static void main(String[] args) {
        // make scanner object for taking input
        Scanner scanner = new Scanner(System.in);

        // prompt the user and take input
        System.out.print("Please enter a ISBN: ");
        String line = scanner.next();

        // turn the input to a char array
        char[] items = line.toCharArray();
        // declare a sum as 0
        int sum = 0;

        // make an offset in case the user adds a - before the checksum
        int offset = 1;
        if (items[items.length-2] == '-') offset = 2;

        // iterate over all the digits except the checksum and add them to the running total
        for (int i = 0; i<items.length-offset; i++){
            sum += charToInt(items[i])*(i+1);
        }

        // declare a checksum variable
        int checkSum;
        // get the last character
        char last = items[items.length-1];
        // check if its a x therefore 10
        if (last == 'x' || last == 'X') checkSum = 10;
        // otherwise get the number
        else checkSum = charToInt(last);

        // check the the sum modulo 11 is the checksum and put in a variable
        boolean confirms = (sum % 11) == checkSum;

        // print that its valid or invalid
        if (confirms) System.out.println("This is a valid ISBN");
        else System.out.println("This ISBN is invalid");
    }

    // function that takes a character and returns an integer
    private static int charToInt(char input){
        return Integer.parseInt(String.valueOf(input));
    }
}
