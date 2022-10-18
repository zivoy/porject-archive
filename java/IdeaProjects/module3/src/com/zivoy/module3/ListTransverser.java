package com.zivoy.module3;

import java.util.ArrayDeque;
import java.util.Deque;

public class ListTransverser {
    Deque<Integer> stack;

    public ListTransverser() {
        stack = new ArrayDeque<>();
    }

    public int[] regurgitate(int[] input) {
        stack.clear();

        int[] output = new int[input.length];

        for (int i = 0; i < input.length; i++) {
            int next = -1;

            for (Integer integer : stack) {
                if (integer < input[i]) {
                    next = integer;
                    break;
                }
            }

            output[i] = next;
            stack.addFirst(input[i]);
        }
        return output;
    }
}
