package com.zivoy.assignment2.Question4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Ziv Shalit
 * @Assignment 2
 * @Question 4
 * @Date 2020/02/28
 */

public class Question4 {
    public static void main(String[] args) {
        // make the random object for generating random numbers
        Random random = new Random();
        // make an arraylist of integers
        ArrayList<Integer> array = new ArrayList<>();

        // loop 100 times
        for (int i = 0; i<100;i++)
            // add a random number that is 0<= n <= 20 to list
            array.add(random.nextInt(21));

        // make a array of 0's that has the size of the array list
        int[] newArray = new int[array.size()];
        // keep curr index
        int idx=0;
        // iterate over all the items in the arraylist
        for (int i: array){
            // if its not 0
            if (i != 0) {
                // add it to the array
                newArray[idx] = i;
                // and increase the index
                idx++;
            }
        }
        // the rest will automatically be 0's

        // print before
        System.out.print("before: ");
        System.out.println(array);
        // print after
        System.out.print("after:  ");
        System.out.println(Arrays.toString(newArray));
    }
}
