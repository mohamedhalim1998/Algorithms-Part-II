package week5.ProgrammingAssignment;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private final static int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        int[] table = new int[R];
        for (int i = 0; i < R; i++) {
            table[i] = i;
        }
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            BinaryStdOut.write(table[c], 8);
            for (int i = 0; i < R; i++) {
                if (table[i] < table[c]) {
                    table[i]++;
                }
            }
            table[c] = 0;
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        int[] table = new int[R];
        for (int i = 0; i < R; i++) {
            table[i] = i;
        }
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int index = -1;
            for (int i = 0; i < 256; i++) {
                if (table[i] == c) {
                    BinaryStdOut.write(i,8);
                    index = i;
                    break;
                }
            }


            for (int i = 0; i < R; i++) {
                if (table[i] < table[index]) {
                    table[i]++;
                }
            }
            table[index] = 0;
        }
        BinaryStdOut.flush();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        }
        if (args[0].equals("+")) {
            decode();
        }
    }

}