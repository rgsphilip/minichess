/**
 * Created by rPhilip on 4/28/17.
 */
public class Piece {
    int xCord;
    int yCord;
    char pieceType;
    public Piece(char piece, int xCord, int yCord) {
        this.pieceType = piece;
        this.xCord = xCord;
        this.yCord = yCord;

    }

    public int getxCord() {
        return xCord;
    }

    public void setxCord(int xCord) {
        this.xCord = xCord;
    }

    public int getyCord() {
        return yCord;
    }

    public void setyCord(int yCord) {
        this.yCord = yCord;
    }

    public char getPieceType() {
        return pieceType;
    }

    public void setPieceType(char pieceType) {
        this.pieceType = pieceType;
    }
}
