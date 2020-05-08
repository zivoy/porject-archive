package com.zivoy.module3;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ListTransverser listTransverser = new ListTransverser();

        int numOfEntrys = -1;
        do {
            System.out.print("number of entries: "); // prompt -- can be commented out
            String input = scanner.nextLine();
            try {
                numOfEntrys = Integer.parseInt(input);
                if (numOfEntrys < 0) {
                    System.out.println("Must be positive number");
                }
            } catch (Exception ignored) {
                System.out.println("That does not seem to be a number");
            }
        } while (numOfEntrys < 0);

        int[] array = new int[numOfEntrys];

        while (true) {
            System.out.print("space separated list: "); // prompt -- can be commented out
            String input = scanner.nextLine();
            String[] inputs = input.split(" +");
            boolean success = true;
            for (int i = 0; i < inputs.length; i++) {
                if (i>=numOfEntrys)
                    break;
                int val;
                try {
                    val = Integer.parseInt(inputs[i]);
                } catch (Exception ignored) {
                    success = false;
                    System.out.println("Entry \"" + inputs[i] + "\" does not seem to be a valid integer");
                    break;
                }
                array[i] = val;
            }
            if (!success)
                continue;
            break;
        }
//        System.out.println(formatArray(array));
        System.out.println(formatArray(listTransverser.regurgitate(array)));
    }

    public static String formatArray(int[] array) {
        String[] input = new String[array.length];
        for (int i=0;i<array.length;i++){
            input[i] = String.valueOf(array[i]);
        }
        return formatArray(input);
    }

    public static String formatArray(String[] array) {
        return String.join(" ", array);
    }
}