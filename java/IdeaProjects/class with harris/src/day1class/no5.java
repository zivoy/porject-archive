package day1class;

import java.io.IOException;

public class no5 {
    public static void main(String[] args) throws IOException {
        char ch, ignore, answer = 'K';

        do {
            System.out.println("im thinking of a letter between A and Z");
            System.out.println("can you guess what it is?: ");
            ch = (char) (System.in.read() & 0x5f);
            do {
                ignore = (char) System.in.read();
            } while (ignore != '\n');

            if (ch == answer) System.out.println("you guessed it");
            else{
                System.out.println("wrong answer");
                if (ch < answer) System.out.println("too high");
                else System.out.println("too low");
                System.out.println("Try again");
            }
        } while (ch != answer);
    }
}