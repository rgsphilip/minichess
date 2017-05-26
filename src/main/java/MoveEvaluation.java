/**
 * Created by rPhilip on 5/22/17.
 */
public class MoveEvaluation {
    int depth;
    int score;
    MoveEval moveEval;
    enum MoveEval {WIN, LOSS, DRAW, PLAYING}
    public MoveEvaluation(MoveEval eval, int depth, int score) {
        this.moveEval = eval;
        this.depth = depth;
        this.score = score;
    }

    public MoveEvaluation(MoveEval eval, int depth) {
        this.moveEval = eval;
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    public int getScore() {
        return score;
    }

    public MoveEval getMoveEval() {
        return moveEval;
    }
}
