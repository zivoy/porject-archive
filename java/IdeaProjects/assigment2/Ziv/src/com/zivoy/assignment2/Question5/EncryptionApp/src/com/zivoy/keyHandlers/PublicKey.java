package com.zivoy.keyHandlers;

// class that handles public keys
public class PublicKey extends Key {
    // public key constructor
    public PublicKey(int part1, int part2) {
        this.part1 = part1; // set values
        this.part2 = part2;
    }

    // get the public key from a string
    public static PublicKey fromString(String key) {
        String deKey = String.valueOf(toNum(key)); // turn the string into numbers
        int[] parts = splitKey(deKey); // split it into parts
        return new PublicKey(parts[0], parts[1]); // make new key
    }

    // decode a message
    public String encode(String message, int n) {
        String output = processNums(toAscii(message));  // turn string into ascii and then map it to the correct numbers and join back up into a string
        for (int i = 0; i < n; i++) { // iterate over number of time to encrypt
            output = processNums(crypt(processText(output))); // turn to numbers encrypt it and then turn back into text
        }
        return output.replaceAll("^ +", ""); // get rid of all leading spaces
    }

    // same thing but default number of times to encrypt is set to 1
    public String encode(String message) {
        return encode(message, 1);
    }

    // override the getKey to join up string correctly
    @Override
    public String getKey() {
        String n = String.format("%07d", this.part1); // pad part 1 to 7 digits
        String e = String.format("%07d", this.part2); // pad part 2 to 7 digits

        return toEnc(Long.parseLong(n + e)); // turn to a string
    }

    // override default get string to return get key
    @Override
    public String toString() {
        return this.getKey();
    }

    // validate the public key
    public boolean validate() {
        String output;
        try {
            output = this.encode("Hello, World!"); // encode the message
        } catch (Exception e) { // if there was an error then its invalid
            return false;
        }
        for (char i : output.toCharArray()) {
            if (!((i <= 'z' && i >= 'a') || (i <= 'Z' && i >= 'A') || i == ' ')) // if any of the characters ware not valid characters then its invalid
                return false;
        }
        return true; // otherwise its valid
    }
}
