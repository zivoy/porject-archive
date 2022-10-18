package com.zivoy.module3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BracketConfirmer {
    private final Map<Character, Character> brackets;
    private final Set<Character> opens;
    private final Set<Character> closes;

    public BracketConfirmer() {
        this.brackets = new HashMap<>();
        brackets.put('{', '}');
        brackets.put('[', ']');
        brackets.put('(', ')');

        closes = new HashSet<>();
        opens = new HashSet<>();
        for (Map.Entry<Character, Character> i : brackets.entrySet()) {
            opens.add(i.getKey());
            closes.add(i.getValue());
        }
    }

    public boolean confirm(String string) {
        for (int i = 0; i < string.length(); i++) {
            char currChar = string.charAt(i);

            if (opens.contains(currChar))
                i = confirm(string.substring(i + 1), currChar, i+1);

            if (i == -1 || closes.contains(currChar))
                return false;
        }
        return true;
    }

    private int confirm(String string, char lastBracket, int lastidx) {
        for (int i = 0; i < string.length(); i++) {
            char currChar = string.charAt(i);

            // logic for handling opening of brackets
            if (opens.contains(currChar))
                i = confirm(string.substring(i + 1), currChar, i+1);

            if (i == -1)
                break;

            // logic for handling closing of brackets
            if (closes.contains(currChar)) {
                if (currChar != brackets.get(lastBracket))
                    return -1;
                else
                    return lastidx + i;
            }
        }
        return -1;
    }
}
