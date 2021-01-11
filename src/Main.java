import edu.princeton.cs.algs4.Picture;
import week2.ProgrammingAssignment.SeamCarver;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Picture picture = new Picture(new File("6x5.png"));
        SeamCarver seamCarver = new SeamCarver(picture);
        int[][] a = new int[picture.width()][picture.height()];
//        for (int i = 0; i < picture.height(); i++) {
//            for (int j = 0; j < picture.width(); j++) {
//                System.out.print("(" + j + "," + i + ")" + " ");
//            }
//            System.out.println();
//        }
//        for (int i = 0; i < picture.height(); i++) {
//            for (int j = 0; j < picture.width(); j++) {
//                System.out.print(seamCarver.energy(j,i) + " ");
//            }
//            System.out.println();
//        }
        for(int i = 0 ; i < 4; i++){
            seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
            System.out.println(seamCarver.picture());
        }
    }
}
