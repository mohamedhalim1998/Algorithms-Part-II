package week5.ProgrammingAssignment;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class BurrowsWheeler {
    private static final int R = 256;

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray suffixArray = new CircularSuffixArray(s);
        char[] last = new char[s.length()];
        int first = -1;
        int len = s.length();
        for (int i = 0; i < s.length(); i++) {
            int index = suffixArray.index(i);
            if (index == 0) {
                first = i;
            }
            index = (len - 1 + index) % len;
            last[i] = s.charAt(index);
        }
        BinaryStdOut.write(first);
        for (char c : last) {
            BinaryStdOut.write(c, 8);
        }
        BinaryStdOut.flush();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String lastChar = BinaryStdIn.readString();
        char[] firstChar = lastChar.toCharArray();
        Arrays.sort(firstChar);
        int[] next = constructNext(firstChar, lastChar);
        int current = first;
        for (int i = 0; i < next.length; i++) {
            BinaryStdOut.write(firstChar[current]);
            current = next[current];
        }
        BinaryStdOut.flush();
    }

    private static int[] constructNext(char[] firstChar, String lastChar) {
        int[] next = new int[lastChar.length()];
        int[] indexOf = new int[R];
        Arrays.fill(indexOf, -1);
        for (int i = 0; i < firstChar.length; i++) {
            char c = firstChar[i];
            next[i] = lastChar.indexOf(c, indexOf[c] + 1);
            indexOf[c] = next[i];
        }
      //  System.out.println(Arrays.toString(next));
        return next;
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            transform();
        } else if (args[0].equals("+")) {
            inverseTransform();
        }
    }
}