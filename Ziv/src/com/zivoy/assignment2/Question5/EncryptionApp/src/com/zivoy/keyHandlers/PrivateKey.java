package com.zivoy.keyHandlers;

// class for private keys
public class PrivateKey extends Key {
    private int publicPart2; // an extra public key part 2 variable

    // make a private key and return a object
    public PrivateKey() {
        PrivateKey t = makePrivateKey(); // generate private key
        this.part1 = t.part1; // fill out part 1
        this.part2 = t.part2; // part 2
        this.publicPart2 = t.publicPart2; // public key part2
    }

    public PrivateKey(int d, int n, int e) { // same thing but dont make a new key
        this.part1 = n;
        this.part2 = d;
        this.publicPart2 = e;
    }

    public PrivateKey(int keyPart1, int keyPart2) {  // same as above but this time dont fill out the public key part2
        this.part1 = keyPart1;
        this.part2 = keyPart2;
        this.publicPart2 = -1;
    }

    // reverse a string
    private static String reverse(String string) {
        StringBuilder stringBuilder = new StringBuilder(string);

        stringBuilder.reverse();

        return stringBuilder.toString();
    }

    // make a private key from a string
    public static PrivateKey fromString(String key) {
        String deKey = reverse(String.valueOf(toNum(key))); // convert string key to a number and reverse the order
        int[] parts = splitKey(deKey);  // split it into parts
        return new PrivateKey(parts[0], parts[1]); // and make a new key object
    }

    // generate a new key
    public PrivateKey makePrivateKey() {
        int p = randPrime(minPrime, 1423);  // pick a random prime between 1009 and 1423
        int q;
        do {
            q = randPrime(minPrime, 7027); // pick a random prime between 1009 and 7027
        } while (p == q);  // if it equals to p then pick again
        int n = p * q;  // get n by multiplying them
        int yn = (q - 1) * (p - 1);   // get the totient of the 2 primes
        int e;
        do {
            e = getRandom(1000000, yn); // pick a random number between 1000000 and the totient
        } while (!areCoprime(e, yn)); // of its not coprime with the totient then pick again
        int d = modInverse(e, yn);   // get d by doing the modular inverse of e and the totient

        String test = "Hello, World!";
        if (!new PrivateKey(n, d).decode((new PublicKey(n, e).encode(test))).equals(test))  // test that its a vaild key
            return makePrivateKey(); // if it isent the make a new one
        return new PrivateKey(d, n, e); // otherwise return the key
    }

    // find the public key part 2 from the private key
    private int findE(int n, int d) {
        int p, q, e;
        do {
            p = randPrime(minPrime, 1423);
            q = n / p;
        } while (!isPrime(q));
        int yn = (q - 1) * (p - 1);

        for (int i = 1000000; i <= yn; i++) {
            e = getRandom(1000000, yn);
            if (areCoprime(e, yn) && d == modInverse(e, yn))
                return e;
        }
        return -1;
    }

    // override the get key method to return the string reversed
    @Override
    public String getKey() {
        String n = String.format("%07d", this.part1); // pad part 1 to 7 digits
        String d = String.format("%07d", this.part2); // pad part 2 to 7 digits

        return toEnc(Long.parseLong(reverse(n + d))); // reverse the string end turn it into a string
    }

    // override the default get string to return getKey
    @Override
    public String toString() {
        return this.getKey();
    }

    // decode a string
    public String decode(String message, int n) {
        StringBuilder messageBuilder = new StringBuilder(message);
        while (messageBuilder.length() % 3 != 0) { // if the string is not a multiple of 3
            messageBuilder.insert(0, " "); // pad it till it is
        }
        String output = messageBuilder.toString();
        for (int i = 0; i < n; i++) { // for the number of times to decrypt
            output = processNums(crypt(processText(output))); // turn it into numbers decrypt it and turn it back into a string
        }
        return toText(processText(output)); // return the string
    }

    // same thing but set the default encryption count to 1
    public String decode(String message) {
        return decode(message, 1);
    }

    // make a public key
    public PublicKey makePublic() {
        if (this.publicPart2 == -1)
            this.publicPart2 = findE(this.part1, this.part2);  // if public key part2 is unknown find it // should never really be used
        return new PublicKey(this.part1, this.publicPart2);  // make a new public key
    }
}
