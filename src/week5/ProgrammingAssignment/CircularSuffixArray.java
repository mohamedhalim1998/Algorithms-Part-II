package week5.ProgrammingAssignment;

import edu.princeton.cs.algs4.SuffixArray;

import java.util.Arrays;

public class CircularSuffixArray {
    private int length;
    private Suffix[] suffixes;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null)
            throw new IllegalArgumentException();
        length = s.length();
        this.suffixes = new Suffix[length];
        for (int i = 0; i < length; i++)
            suffixes[i] = new Suffix(s, i);
        Arrays.sort(suffixes);
    }

    // length of s
    public int length() {
        return length;
    }

    // returns index of ith sorted suffix

    public int index(int i) {
        if (i < 0 || i >= suffixes.length) throw new IllegalArgumentException();

        return suffixes[i].index;

    }

    // unit testing (required)
    public static void main(String[] args) {

    }

    private static class Suffix implements Comparable<Suffix> {
        private final String text;
        private final int index;

        private Suffix(String text, int index) {
            this.text = text;
            this.index = index;
        }

        private int length() {
            return text.length();
        }

        private char charAt(int i) {
            i = (i + index) % length();
            return text.charAt(i);
        }

        public int compareTo(Suffix that) {
            if (this == that) return 0;
            for (int i = 0; i < length(); i++) {
                if (this.charAt(i) < that.charAt(i)) return -1;
                if (this.charAt(i) > that.charAt(i)) return +1;
            }
            return 0;
        }

        public String toString() {
            return text.substring(index);
        }
    }
}