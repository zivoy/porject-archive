package com.zivoy.keyHandlers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.*;

// abstract key class has all base operations needed for both public and private keys
public class Key {
    // variables
    public final Integer[] cached_primes;  // array of primes
    public int part1;  // first part of key
    public int part2;  // second part of key
    Random random = new Random();  // a random object for generating random numbers
    int minPrime = 1009;  // this minimum prime
    int maxPrime = 7028;  // the maximum prime

    {
        Integer[] cached_primes1;  // create a temp array
        ArrayList<Integer> cached_primes_temp = new ArrayList<>();  // arrayList
        for (int i = minPrime; i < maxPrime; i++) {  // iterate between min and max prime
            if (isPrime(i)) cached_primes_temp.add(i);  // and populate the array list if its prime
        }
        cached_primes1 = new Integer[cached_primes_temp.size()];  // put it in the temp array
        cached_primes1 = cached_primes_temp.toArray(cached_primes1);
        cached_primes = cached_primes1; // then put the temp array in the final variable
    }

    // function to determine the greatest common denominator
    public static int gcd(int a, int b) {
        if (b == 0) return a;  // if b is 0 return a
        return gcd(b, a % b);  // else recursion flip variables and modulo the second
    }

    // function tha checks if two numbers are co-prime
    public static boolean areCoprime(int a, int b) {
        return gcd(a, b) == 1;
    }

    // function that does modular inverse
    public static int modInverse(int a, int m) {
        long Ta = a % m;
        for (long x = 1; x < (long) m; x++) {  // iterate between 1 and m
            if ((Ta * x) % (long) m == 1) // if (a mod m) * the current number % m == 1 then return the current number
                return (int) x;
        }
        return 1;  // otherwise return 1
    }
    /*public static int modInverse(int a, int b){
        BigInteger tempA = BigInteger.valueOf(a);
        BigInteger tempB = BigInteger.valueOf(b);
        BigInteger tempC = tempA.modInverse(tempB);
        return tempC.intValue();
    }*/

    // function that checks if the number is prime
    public static boolean isPrime(int n) {
        if (n % 2 == 0) return false;  // if its even then no
        for (int i = 3; i < ceil(sqrt(n)); i += 2) {   // go up by 2s and go between 3 and the square root of the number
            if (n % i == 0) return false;  // if the remainder of the number divided by anything then no
        }
        return true;  // else yes
    }

    // get a random number between a minimum and maximum
    public static double getRandom(double min, double max) {
        return (random() * (max + 1 - min)) + min;
    }

    // get a random number but integer edition
    public static int getRandom(int min, int max) {
        return (int) Math.min(max, Math.max(min, round(getRandom((double) min, max))));
    }

    // split a key into part 1 and 2
    public static int[] splitKey(String key) {
        int part1 = Integer.parseInt(key.substring(0, (int) round(key.length() / 2.))); // get the first part
        int part2 = Integer.parseInt(key.substring((int) round(key.length() / 2.))); // get the second part
        return new int[]{part1, part2}; // return them
    }

    // convert an arrayList into a primitive long array
    private static long[] convertPrimitive(ArrayList<Long> in) {
        long[] out = new long[in.size()];
        for (int i = 0; i < in.size(); i++) {
            out[i] = in.get(i);
        }
        return out;
    }

    // convert an Long array into a primitive long array
    private static long[] reverseArrayList(long[] in) {
        ArrayList<Long> revArrayList = new ArrayList<>();
        for (int i = in.length - 1; i >= 0; i--) {
            revArrayList.add(in[i]);
        }
        return convertPrimitive(revArrayList);
    }

    // reverse the order of an arraylist
    private static long[] reverseArrayList(ArrayList<Long> in) {
        return reverseArrayList(convertPrimitive(in));
    }

    // convert the base of a number from 10 to base_to
    public static long[] convertBase(long n, int base_to) {
        if (n == 0L)  // if its zero
            return new long[]{0L}; // then return 0 in a list
        ArrayList<Long> digits = new ArrayList<>(); // otherwise
        while (n > 0) {  // while the number is bigger then 0
            digits.add(n % base_to);  // add to the array the remainder of what happens when you divide by the new base
            n = Math.floorDiv(n, base_to);  // clip the remainder
        }
        return reverseArrayList(digits); // reverse it because its backwards
    }

    // convert an array of numbers (a singular number) to a new base
    public static long[] convertBase(long[] n, int base_to) {
        return convertBase(getNumFromList(n, 10), base_to);
    }

    // convert from a base to a new base
    public static long[] convertBase(long n, int base_to, int base_from) {
        return convertBase(new long[]{n}, base_to, base_from);
    }

    // convert an array(singular number) to a new base from a base
    public static long[] convertBase(long[] n, int base_to, int base_from) {
        long[] nLs = reverseArrayList(n);  // reverse the array
        long newnum = getNumFromList(nLs, base_from); // convert the number to base 10
        return convertBase(newnum, base_to); // convert it to new base
    }

    private static long getNumFromList(long[] list, int base) {
        long sum = 0;
        for (int i = 0; i < list.length; i++) {
            sum += list[i] * Math.pow(base, i);  // multiply the number by the base to the power of its position
        }
        return sum; // return the number
    }

    // map a string to a number
    public static long toNum(String inText) {
        ArrayList<Long> output = new ArrayList<>();
        long character;
        for (char i : inText.replaceAll("^ +", "").toCharArray()) {  // get rid of all leading spaces and iterate of characters
            character = (long) (i) - 39;  // take the ascii value of the number and subtract 39
            if (i > 90)  // if its still over 90
                character = (long) (i) - 97; // subtract another 97
            output.add(character); // add the character
        }
        long[] out = convertBase(convertPrimitive(output), 10, 52);  // convert the numbers from base 52 to base 10
        StringBuilder message = new StringBuilder();
        for (long i : out) {
            message.append(i);  // build a string from the characters
        }
        return Long.parseLong(message.toString()) - 2080L; // get a long and subtract 2080 to offset it a bit
    }

    // get a random prim number from cached primes
    public final int randPrime(int start, int end) {
        int rand;
        do {
            rand = cached_primes[random.nextInt(cached_primes.length)];  // pick a random number
        } while ((start <= rand) && (rand < end)); // repeat until its between limits
        return rand; // return prime
    }

    private long enAndDecrypt(long message) {
        BigInteger temp = BigInteger.valueOf(message);
        BigInteger temp1 = BigInteger.valueOf(this.part1);
        BigInteger temp2 = BigInteger.valueOf(this.part2);
        BigInteger temp3 = temp.modPow(temp2, temp1);      // this does the operation (message ** part2) % part1

        return temp3.intValue(); // return the value
    }

    // convert a string to ascii characters
    public final long[] toAscii(String text) {
        char[] chrs = text.toCharArray();
        Long[] out = new Long[chrs.length]; // make a new array
        for (int i = 0; i < chrs.length; i++) {
            out[i] = (long) chrs[i]; // convert to a number and add it to array
        }
        return convertPrimitive(out); // return the array
    }

    // turn a list of ascii characters and return a string
    public final String toText(long[] chrs) {
        StringBuilder out = new StringBuilder();
        for (long chr : chrs) {
            out.append((char) chr); // get the char value of num and add it to string
        }
        return out.toString();  // return it
    }

    // encrypt and decrypt message
    public final long[] crypt(long[] inMessage) {
        ArrayList<String> newMessage = new ArrayList<>();

        for (int i = 0; i < inMessage.length; i += 2) {
            if ((i + 1) < inMessage.length) {  // group message in junks of 6 characters
                newMessage.add(String.format("%03d", inMessage[i]) + String.format("%03d", inMessage[i + 1]));
            } else {
                newMessage.add(String.format("%03d", inMessage[i]));  // only 3 if it cant
            }
        }

        ArrayList<Long> fMessage = new ArrayList<>();

        for (String i : newMessage) {
            long tempMessage = enAndDecrypt(Long.parseLong(i));  // en/decrypt the message
            fMessage.add(tempMessage / 1000); // split it again to 2 parts
            fMessage.add(tempMessage % 1000);
        }

        if (fMessage.get(fMessage.size() - 2) == 0)
            fMessage.remove(fMessage.size() - 2);  // remove the second to last one if its not a number

        return convertPrimitive(fMessage); // return list of numbers
    }

    // convert Long array to long array
    private long[] convertPrimitive(Long[] in) {
        long[] out = new long[in.length];
        for (int i = 0; i < in.length; i++) {
            out[i] = in[i];  // iterate and but in new list
        }
        return out;
    }

    // map number to string
    public final String toEnc(long inNum) {
        StringBuilder output = new StringBuilder();
        inNum += 2080;  // add 2080 to the number
        for (Long i : convertBase(inNum, 52)) { // convert from base 10 to base 52
            char charecter = (char) (i + 97);  // add 97 and turn it into a character
            if (i + 65 > 90) {  // if when you add 65 its over 90
                charecter = (char) (i + 39); // then instead add 39 and turn it into a character
            }
            output.append(charecter); // add to string
        }
        return String.format("%1$3s", output.toString()); // pad it with spaces and return it
    }

    // same as above but for a list
    public final String toEnc(long[] inNum) {
        return toEnc(inNum[0]);
    }

    // split a string into sizable chunks
    public final String[] splitString(String string, int cut, String pad) {
        ArrayList<String> output = new ArrayList<>();
        for (int i = 0; i < string.length(); i += cut) { // iterate jumping up by size of cut
            String prt = string.substring(i, i + cut); // cut the string into size cut
//            if (!pad.equals("")) {
//                prt = String.format("%1$-" + cut + "s", prt);
//                //"{0:{fill}>{amount}}".format(prt, fill = "-", amount = cut);
//            }
            output.add(prt); // add part to string
        }
        String[] out = new String[output.size()];
        out = output.toArray(out);  // convert to array and return it
        return out;
    }

    // turn string into array if numbers
    public final long[] processText(String text) {
        ArrayList<Long> output = new ArrayList<>();
        String[] uInput = splitString(text, 3, " "); // split the string into parts of length 3
        for (String i : uInput) {
            output.add((toNum(i))); // map it into a number and add to string
        }
        return convertPrimitive(output); // return it
    }

    // turn array of numbers into string
    public final String processNums(long[] nums) {
        StringBuilder output = new StringBuilder();
        for (long i : nums) { // iterate over the numbers
            output.append(toEnc(i));  // turn then number to a string and add to string
        }
        return output.toString(); // return string
    }

    // return key meant to be overridden
    public String getKey() {
        return String.valueOf(this.part1) + this.part2;
    }
}
