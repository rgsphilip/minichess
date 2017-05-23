import java.util.Objects;

/**
 * Created by rPhilip on 4/24/17.
 */
public class Move {
    Square toSquare;
    Square fromSquare;
    public Move(Square fromSquare, Square toSquare) {
        this.toSquare = toSquare;
        this.fromSquare = fromSquare;
    }

    public Square getToSquare() {
        return toSquare;
    }

    public Square getFromSquare() {
        return fromSquare;
    }

    @Override
    public boolean equals(Object o) {
        //self check
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Move move = (Move) o;
        return Objects.equals(toSquare, move.toSquare) && Objects.equals(fromSquare, move.fromSquare);
    }
}
