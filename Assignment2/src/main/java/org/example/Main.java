package org.example;

import java.io.IOException;
import java.math.BigInteger;

public class Main {
    public static void main(String[] args) throws IOException {
//        org.openjdk.jmh.Main.main(args);
        BigInteger fiveThousand = new BigInteger ("5000");
        BigInteger fiftyThousand = new BigInteger ("50000");
        BigInteger fiveHundredThousand = new BigInteger ("500000");
        BigInteger total = BigInteger.ZERO;
        total.add(fiveThousand);
        total.add(fiftyThousand);
        total.add(fiveHundredThousand);
        System.out.println(total);
    }
}