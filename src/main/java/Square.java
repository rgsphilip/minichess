import java.util.Objects;

/**
 * Created by rPhilip on 4/24/17.
 */
public class Square {
    int xCord;
    int yCord;
    public Square(int xCord, int yCord) {
        this.xCord = xCord;
        this.yCord = yCord;
    }

    public int getxCord() {
        return xCord;
    }

    public int getyCord() {
        return yCord;
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
        Square square = (Square) o;
        return Objects.equals(xCord, square.xCord) && Objects.equals(yCord, square.yCord);
    }
}
