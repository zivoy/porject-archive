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
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter a ISBN: ");
        String line = scanner.next();
        char[] items = line.toCharArray();
        int sum = 0;

        int offset = 1;
        if (items[items.length-2] == '-') offset = 2;

        for (int i = 0; i<items.length-offset; i++){
            sum += charToInt(items[i])*(i+1);
        }
        int checkSum;
        char last = items[items.length-1];
        if (last == 'x' || last == 'X') checkSum = 10;
        else checkSum = charToInt(last);

        boolean confirms = (sum % 11) == checkSum;

        if (confirms) System.out.println("This is a valid ISBN");
        else System.out.println("This ISBN is invalid");
    }
    private static int charToInt(char input){
        return Integer.parseInt(String.valueOf(input));
    }
}
