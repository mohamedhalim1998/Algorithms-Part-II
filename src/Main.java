import edu.princeton.cs.algs4.In;
import week4.ProgrammingAssignment.BoggleBoard;
import week4.ProgrammingAssignment.BoggleSolver;


public class Main {
    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        BoggleBoard boggleBoard = new BoggleBoard("board.in");
        BoggleSolver solver = new BoggleSolver(new In("dic.in").readAllLines());
        int score = 0;
        int count = 0;
        for (String s : solver.getAllValidWords(boggleBoard)) {
            score += solver.scoreOf(s);
            count++;
        }
        System.out.println(count);
        System.out.println(score);
        time = System.currentTimeMillis() - time;
        System.out.println(time / 60);

    }
}
