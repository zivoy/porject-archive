package day1class;

public class no3 {
    static char ch1 ='A';
    static char ch2 = 'G';

    public static void main(String[] main){
        switch (ch1){
            case 'A': System.out.println("this A of the outer part of switch");
            switch (ch2) {
                case 'A':
                    System.out.println("This A is part of the inner switch");
                    break;
                case 'B':
            }
            break;
            case 'B':
        }
    }
}
