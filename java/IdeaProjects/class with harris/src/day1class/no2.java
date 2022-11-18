package day1class;

import java.io.IOException;

public class no2 {
    public static void main(String[] args) throws IOException {
            char ch, answer = 'K';

            System.out.println("im thinking of a letter between A and Z");
            System.out.println ("can you geuss what it is?: ");
            ch = (char) (System.in.read() & 0x5f);
            if (ch == answer) System.out.println("you guessed it");
            else System.out.println("wrong answer");

    }
}
