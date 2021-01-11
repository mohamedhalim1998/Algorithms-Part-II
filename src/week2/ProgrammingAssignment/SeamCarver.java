package week2.ProgrammingAssignment;

import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.Arrays;
import java.util.Stack;

public class SeamCarver {
    private Picture picture;
    private double[][] energies;
    private int height;
    private int width;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException();
        this.picture = new Picture(picture);
        height = picture.height();
        width = picture.width();
        constructEnergies();
    }

    private void constructEnergies() {
        energies = new double[height()][width()];
        for (int x = 0; x < width() - 1; x++) {
            for (int y = 1; y < height(); y++) {
                energies[y][x] = energy(x, y);
            }
        }
    }


    // current picture
    public Picture picture() {
        return new Picture(picture);
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1)
            throw new IllegalArgumentException(String.format("(%d, %d)", x, y));
        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1)
            return 1000;

        if (energies[y][x] != 0) {
            return energies[y][x];
        }
        return Math.sqrt(deltaSquareX(x, y) + deltaSquareY(x, y));
    }

    private double deltaSquareX(int x, int y) {
        Color prev = picture.get(x - 1, y);
        Color next = picture.get(x + 1, y);


        return deltaSquare(prev, next);
    }

    private double deltaSquareY(int x, int y) {
        Color prev = picture.get(x, y - 1);
        Color next = picture.get(x, y + 1);
        return deltaSquare(prev, next);
    }

    private double deltaSquare(Color prev, Color next) {
        int r = (prev.getRed() - next.getRed()) * (prev.getRed() - next.getRed());
        int b = (prev.getBlue() - next.getBlue()) * (prev.getBlue() - next.getBlue());
        int g = (prev.getGreen() - next.getGreen()) * (prev.getGreen() - next.getGreen());
        return r + b + g;
    }


    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        if (height() == 1) {
            int[] path = new int[width()];
            Arrays.fill(path, 0);
            return path;
        }
        int[][] edgeTo = new int[height()][width()];
        double[][] distTo = new double[height()][width()];
        calculateDistHorizontal(distTo, edgeTo);
        int index = getMinIndexHorizontal(distTo);
        return getPathHorizontal(index, edgeTo);
    }

    private int[] getPathHorizontal(int index, int[][] edgeTo) {
        Stack<Integer> st = new Stack<>();
        int x = width() - 1;
        st.push(index);
        while (edgeTo[index][x] != -1) {
            index = edgeTo[index][x];
            x--;
            st.push(index);
        }
        int[] path = new int[width()];
        int i = 0;
        while (!st.isEmpty()) {
            path[i++] = st.pop();
        }

        return path;
    }

    private int getMinIndexHorizontal(double[][] distTo) {
        double min = Double.POSITIVE_INFINITY;
        int index = -1;
        for (int i = 0; i < height(); i++) {
            if (min > distTo[i][width() - 1]) {
                min = distTo[i][width() - 1];
                index = i;
            }
        }
        return index;
    }

    private void calculateDistHorizontal(double[][] distTo, int[][] edgeTo) {
        initDistances(distTo, edgeTo, true);
        for (int x = 0; x < width() - 1; x++) {
            for (int y = 1; y < height(); y++) {
                if (distTo[y][x] + energy(x + 1, y - 1) < distTo[y - 1][x + 1]) {
                    distTo[y - 1][x + 1] = distTo[y][x] + energy(x + 1, y - 1);
                    edgeTo[y - 1][x + 1] = y;
                }
                if (distTo[y][x] + energy(x + 1, y) < distTo[y][x + 1]) {
                    distTo[y][x + 1] = distTo[y][x] + energy(x + 1, y);
                    edgeTo[y][x + 1] = y;
                }

                if (y + 1 < height() && distTo[y][x] + energy(x + 1, y + 1) < distTo[y + 1][x + 1]) {
                    distTo[y + 1][x + 1] = distTo[y][x] + energy(x + 1, y + 1);
                    edgeTo[y + 1][x + 1] = y;
                }
            }
        }
    }

    private void initDistances(double[][] distTo, int[][] edgeTo, boolean horizontal) {
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                edgeTo[i][j] = -1;
                distTo[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        if (horizontal)
            for (int i = 0; i < height(); i++)
                distTo[i][0] = 1000;
        else
            for (int i = 0; i < width(); i++)
                distTo[i][0] = 1000;
    }


    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        if (width() == 1) {
            int[] path = new int[height()];
            Arrays.fill(path, 0);
            return path;
        }
        int[][] edgeTo = new int[height()][width()];
        double[][] distTo = new double[height()][width()];
        initDistances(distTo, edgeTo, false);
        calculateDistVertical(edgeTo, distTo);
        int index = getMinIndexVertical(distTo);

        return getPathVertical(index, edgeTo);
    }

    private int[] getPathVertical(int index, int[][] edgeTo) {
        Stack<Integer> st = new Stack<>();
        int j = height() - 1;
        st.push(index);
        while (edgeTo[j][index] != -1) {
            index = edgeTo[j][index];
            j--;
            st.push(index);
        }
        int[] path = new int[height()];
        int i = 0;
        while (!st.isEmpty()) {
            path[i++] = st.pop();
        }
        return path;
    }


    private int getMinIndexVertical(double[][] distTo) {
        double min = Double.POSITIVE_INFINITY;
        int index = -1;
        for (int i = 0; i < width(); i++) {
            if (min > distTo[height() - 1][i]) {
                min = distTo[height() - 1][i];
                index = i;
            }
        }
        return index;
    }

    private void calculateDistVertical(int[][] edgeTo, double[][] distTo) {
        for (int y = 0; y < height() - 1; y++) {
            for (int x = 1; x < width(); x++) {
                if (distTo[y][x] + energy(x - 1, y + 1) < distTo[y + 1][x - 1]) {
                    distTo[y + 1][x - 1] = distTo[y][x] + energy(x - 1, y + 1);
                    edgeTo[y + 1][x - 1] = x;
                }
                if (distTo[y][x] + energy(x, y + 1) < distTo[y + 1][x]) {
                    distTo[y + 1][x] = distTo[y][x] + energy(x, y + 1);
                    edgeTo[y + 1][x] = x;
                }
                if (x + 1 < width() && distTo[y][x] + energy(x + 1, y + 1) < distTo[y + 1][x + 1]) {
                    distTo[y + 1][x + 1] = distTo[y][x] + energy(x + 1, y + 1);
                    edgeTo[y + 1][x + 1] = x;
                }
            }
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        validateHorizontalSeam(seam);
        Picture p = new Picture(width, height - 1);
        for (int i = 0; i < width(); i++) {
            boolean removed = false;
            for (int j = 0; j < height() - 1; j++) {
                if (!removed) {
                    if (seam[i] != j) {
                        p.setRGB(i, j, picture.getRGB(i, j));
                    } else {
                        removed = true;
                        p.setRGB(i, j, picture.getRGB(i, j + 1));

                    }
                } else {
                    p.setRGB(i, j, picture.getRGB(i, j + 1));
                }
            }
        }
        picture = p;
        height--;
        constructEnergies();
    }

    private void validateHorizontalSeam(int[] seam) {
        if (seam == null || seam.length != width) {
            throw new IllegalArgumentException();
        }
        for (int i : seam) {
            if (i > height - 1 || i < 0) {
                throw new IllegalArgumentException();
            }
        }
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        validateVerticalSeam(seam);
        Picture p = new Picture(width - 1, height);
        for (int i = 0; i < height(); i++) {
            boolean removed = false;
            for (int j = 0; j < width() - 1; j++) {
                if (!removed) {
                    if (seam[i] != j) {
                        p.setRGB(j, i, picture.getRGB(j, i));
                    } else {
                        removed = true;
                        p.setRGB(j, i, picture.getRGB(j + 1, i));

                    }
                } else {
                    p.setRGB(j, i, picture.getRGB(j + 1, i));
                }
            }
        }
        picture = p;
        width--;
        constructEnergies();
    }

    private void validateVerticalSeam(int[] seam) {
        if (seam == null || seam.length != height()) {
            throw new IllegalArgumentException();
        }
        for (int i : seam) {
            if (i > width() - 1 || i < 0) {
                throw new IllegalArgumentException();
            }
        }
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
    }

    //  unit testing (optional)
    public static void main(String[] args) {

    }

}