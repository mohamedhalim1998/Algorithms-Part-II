import edu.princeton.cs.algs4.In;


public class Main {
    public static void main(String[] args) {
        BoggleBoard boggleBoard = new BoggleBoard("board.in");
        BoggleSolver solver = new BoggleSolver(new In("dic.in").readAllLines());
        int score = 0;
        for(String s : solver.getAllValidWords(boggleBoard)){
            System.out.println(s);
            score += solver.scoreOf(s);
        }
        System.out.println(score);
    }
}
