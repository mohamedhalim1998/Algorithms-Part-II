package week4.ProgrammingAssignment;

import java.util.HashSet;

public class BoggleSolver {
    private Trie dictionary = new Trie();
    private static final int[] neighbors = new int[]{
            -1, -1,
            -1, 0,
            -1, 1,
            0, -1,
            0, 1,
            1, -1,
            1, 0,
            1, 1};
    private int cols;
    private int rows;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null)
            throw new IllegalArgumentException();
        for (String s : dictionary) {
            this.dictionary.add(s);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        HashSet<String> words = new HashSet<>();
        cols = board.cols();
        rows = board.rows();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boolean[] marked = new boolean[rows * cols];
                StringBuilder current = new StringBuilder();
                current.append(board.getLetter(i, j));
                if (board.getLetter(i, j) == 'Q')
                    current.append("U");
                checkWord(board, current, i, j, marked, words);
            }
        }

        return words;
    }

    private void checkWord(BoggleBoard board, StringBuilder current, int i, int j, boolean[] marked, HashSet<String> words) {
        if (current.length() > 2 && dictionary.contains(current.toString())) {
            words.add(current.toString());
        }
        if (!dictionary.hasPrefix(current.toString())) {
            return;
        }
        marked[to1D(i, j)] = true;
        // boolean[] copy = Arrays.copyOf(marked, marked.length);
        //StringBuilder builder = new StringBuilder(current);

        for (int x = 0; x < neighbors.length; x += 2) {
            int iNew = i + neighbors[x];
            int jNew = j + neighbors[x + 1];
            if (isValid(iNew, jNew) && !marked[to1D(iNew, jNew)]) {
                current.append(board.getLetter(iNew, jNew));
                if (board.getLetter(iNew, jNew) == 'Q')
                    current.append("U");
                checkWord(board, current, iNew, jNew, marked, words);
                marked[to1D(iNew, jNew)] = false;
                current.deleteCharAt(current.length() - 1);
                if (current.charAt(current.length() - 1) == 'Q')
                    current.deleteCharAt(current.length() - 1);
            }
        }
    }

    private int to1D(int i, int j) {
        return i * cols + j;
    }

    private boolean isValid(int i, int j) {
        return i >= 0 && i < rows && j < cols && j >= 0;
    }


    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word == null)
            throw new IllegalArgumentException();
        int l = word.length();
        if (l < 3 || !dictionary.contains(word)){
            return 0;
        } else if (l < 5) {
            return 1;
        } else if (l == 5) {
            return 2;
        } else if (l == 6) {
            return 3;
        } else if (l == 7) {
            return 5;
        } else {
            return 11;
        }
    }

    private static class Trie {


        private static final int R = 26;        // extended ASCII

        private Node root;      // root of trie
        private int n;          // number of keys in trie

        // R-way trie node
        private static class Node {
            private Node[] next = new Node[R];
            private boolean isString;
        }


        public boolean contains(String key) {
            if (key == null) throw new IllegalArgumentException("argument to contains() is null");
            Node x = get(root, key, 0);
            if (x == null) return false;
            return x.isString;
        }

        private Node get(Node x, String key, int d) {
            if (x == null) return null;
            if (d == key.length()) return x;
            char c = key.charAt(d);
            return get(x.next[c - 'A'], key, d + 1);
        }


        public void add(String key) {
            if (key == null) throw new IllegalArgumentException("argument to add() is null");
            root = add(root, key, 0);
        }

        private Node add(Node x, String key, int d) {
            if (x == null) x = new Node();
            if (d == key.length()) {
                if (!x.isString) n++;
                x.isString = true;
            } else {
                char c = key.charAt(d);
                x.next[c - 'A'] = add(x.next[c - 'A'], key, d + 1);
            }
            return x;
        }


        public int size() {
            return n;
        }


        public boolean isEmpty() {
            return size() == 0;
        }

        public boolean hasPrefix(String prefix) {
            Node x = get(root, prefix, 0);
            return x != null;
        }


    }
}