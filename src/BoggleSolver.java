import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class BoggleSolver {
    private HashSet<String> dictionary = new HashSet<>();
    private static final int[] neighbors = new int[]{
            -1, -1,
            -1, 0,
            -1, 1,
            0, -1,
            0, 1,
            1, -1,
            1, 0,
            1, 1};
    private int size;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null)
            throw new IllegalArgumentException();
        this.dictionary.addAll(Arrays.asList(dictionary));
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        HashSet<String> words = new HashSet<>();
        size = board.cols();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boolean[] marked = new boolean[size * size];
                StringBuilder current = new StringBuilder();
                current.append(board.getLetter(i, j));
                checkWord(board, current, i, j, marked, words);
            }
        }

        return words;
    }

    private void checkWord(BoggleBoard board, StringBuilder current, int i, int j, boolean[] marked, HashSet<String> words) {
        if (current.length() > 2 && dictionary.contains(current.toString())) {
            words.add(current.toString());
        }
        marked[to1D(i, j)] = true;
        boolean[] copy = Arrays.copyOf(marked, marked.length);
        for (int x = 0; x < neighbors.length; x += 2) {
            int iNew = i + neighbors[x];
            int jNew = j + neighbors[x + 1];
            if (isValid(iNew, jNew) && !marked[to1D(iNew, jNew)]) {
                StringBuilder builder = new StringBuilder(current);
                builder.append(board.getLetter(iNew, jNew));
                checkWord(board, builder, iNew, jNew, copy, words);
                copy[to1D(iNew, jNew)] = false;
            }
        }
    }

    private int to1D(int i, int j) {
        return i * size + j;
    }

    private boolean isValid(int i, int j) {
        return i >= 0 && i < 4 && j < 4 && j >= 0;
    }


    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word == null || !dictionary.contains(word))
            throw new IllegalArgumentException();
        int l = word.length();
        if (l < 3) {
            return 0;
        } else if (l < 5) {
            return 1;
        } else if (l == 5) {
            return 2;
        } else if (l == 6) {
            return 3;
        } else if (l == 7) {
            return 4;
        } else {
            return 11;
        }

    }
}