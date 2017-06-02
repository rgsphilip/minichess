import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Created by rPhilip on 5/7/17.
 */
public class MoveTests {
    char[][] pawnsAndKings1 = {
            {'.', '.', '.', '.', '.'},
            {'.', 'k', '.', '.', '.'},
            {'.', '.', 'P', '.', '.'},
            {'.', '.', 'p', '.', '.'},
            {'.', '.', '.', 'K', '.'},
            {'.', '.', '.', '.', '.'}
    };

    @Test
    public void testPawnsAndKings1White() {
        Board testBoard1 = new Board(pawnsAndKings1, 'W', 10);
        Square wKingSquare = new Square(4, 3);
        Square wPawnSquare = new Square(2, 2);
        ArrayList<Move> outputMoves = testBoard1.moves();
        ArrayList<Move> expectedMoves = new ArrayList<>();
        //where can the white pawn go
        expectedMoves.add(new Move(wPawnSquare, new Square(1, 2)));
        expectedMoves.add(new Move(wPawnSquare, new Square(1, 1)));
        //where can the white king go
        expectedMoves.add(new Move(wKingSquare, new Square(3, 2)));
        expectedMoves.add(new Move(wKingSquare, new Square(3, 3)));
        expectedMoves.add(new Move(wKingSquare, new Square(3, 4)));
        expectedMoves.add(new Move(wKingSquare, new Square(4, 2)));
        expectedMoves.add(new Move(wKingSquare, new Square(4, 4)));
        expectedMoves.add(new Move(wKingSquare, new Square(5, 2)));
        expectedMoves.add(new Move(wKingSquare, new Square(5, 3)));
        expectedMoves.add(new Move(wKingSquare, new Square(5, 4)));
        assertTrue(expectedMoves.containsAll(outputMoves));
        assertTrue(outputMoves.containsAll(expectedMoves));
    }

    @Test
    public void testPawnsAndKings1Black() {
        Board testBoard1 = new Board(pawnsAndKings1, 'B', 10);
        Square bKingSquare = new Square(1, 1);
        Square bPawnSquare = new Square(3, 2);
        ArrayList<Move> outputMoves = testBoard1.moves();
        ArrayList<Move> expectedMoves = new ArrayList<>();
        //where can the white pawn go
        expectedMoves.add(new Move(bPawnSquare, new Square(4, 2)));
        expectedMoves.add(new Move(bPawnSquare, new Square(4, 3)));
        //where can the white king go
        expectedMoves.add(new Move(bKingSquare, new Square(0, 0)));
        expectedMoves.add(new Move(bKingSquare, new Square(0, 1)));
        expectedMoves.add(new Move(bKingSquare, new Square(0, 2)));
        expectedMoves.add(new Move(bKingSquare, new Square(1, 0)));
        expectedMoves.add(new Move(bKingSquare, new Square(1, 2)));
        expectedMoves.add(new Move(bKingSquare, new Square(2, 0)));
        expectedMoves.add(new Move(bKingSquare, new Square(2, 1)));
        expectedMoves.add(new Move(bKingSquare, new Square(2, 2)));
        assertTrue(expectedMoves.containsAll(outputMoves));
        assertTrue(outputMoves.containsAll(expectedMoves));
    }

    char[][] queensDominate = {
            {'q', '.', '.', '.', 'p'},
            {'.', '.', '.', '.', 'p'},
            {'.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.'},
            {'P', '.', '.', '.', '.'},
            {'P', '.', '.', '.', 'Q'}
    };
    @Test
    public void queensDominateTest1White() {
        Board testBoard2 = new Board(queensDominate, 'W', 10);
        Square wQueenSquare = new Square(5, 4);
        Square wPawn = new Square(4, 0);
        ArrayList<Move> outputMoves = testBoard2.moves();
        ArrayList<Move> expectedMoves = new ArrayList<>();
        //where can the pawn go
        expectedMoves.add(new Move(wPawn, new Square(3, 0)));
        //queen moves: horizontal
        expectedMoves.add(new Move(wQueenSquare, new Square(5, 3)));
        expectedMoves.add(new Move(wQueenSquare, new Square(5, 2)));
        expectedMoves.add(new Move(wQueenSquare, new Square(5, 1)));
        //queen moves: vertical
        expectedMoves.add(new Move(wQueenSquare, new Square(4, 4)));
        expectedMoves.add(new Move(wQueenSquare, new Square(3, 4)));
        expectedMoves.add(new Move(wQueenSquare, new Square(2, 4)));
        expectedMoves.add(new Move(wQueenSquare, new Square(1, 4)));
        //queen moves: diagonal
        expectedMoves.add(new Move(wQueenSquare, new Square(4, 3)));
        expectedMoves.add(new Move(wQueenSquare, new Square(3, 2)));
        expectedMoves.add(new Move(wQueenSquare, new Square(2, 1)));
        expectedMoves.add(new Move(wQueenSquare, new Square(1, 0)));
        assertTrue(expectedMoves.containsAll(outputMoves));
        assertTrue(outputMoves.containsAll(expectedMoves));

    }

    @Test
    public void queensDominateTest1Black() {
        Board testBoard2 = new Board(queensDominate, 'B', 10);
        Square bQueenSquare = new Square(0, 0);
        Square bPawn = new Square(1, 4);
        ArrayList<Move> outputMoves = testBoard2.moves();
        ArrayList<Move> expectedMoves = new ArrayList<>();
        //where can the pawn go
        expectedMoves.add(new Move(bPawn, new Square(2, 4)));
        //queen moves: horizontal
        expectedMoves.add(new Move(bQueenSquare, new Square(0, 1)));
        expectedMoves.add(new Move(bQueenSquare, new Square(0, 2)));
        expectedMoves.add(new Move(bQueenSquare, new Square(0, 3)));
        //queen moves: vertical
        expectedMoves.add(new Move(bQueenSquare, new Square(1, 0)));
        expectedMoves.add(new Move(bQueenSquare, new Square(2, 0)));
        expectedMoves.add(new Move(bQueenSquare, new Square(3, 0)));
        expectedMoves.add(new Move(bQueenSquare, new Square(4, 0)));
        //queen moves: diagonal
        expectedMoves.add(new Move(bQueenSquare, new Square(1, 1)));
        expectedMoves.add(new Move(bQueenSquare, new Square(2, 2)));
        expectedMoves.add(new Move(bQueenSquare, new Square(3, 3)));
        expectedMoves.add(new Move(bQueenSquare, new Square(4, 4)));
//        for (int i = 0; i < outputMoves.size(); i++) {
//            System.out.println(outputMoves.get(i).toSquare.xCord + " " + outputMoves.get(i).toSquare.yCord);
//        }
        assertTrue(expectedMoves.containsAll(outputMoves));
        assertTrue(outputMoves.containsAll(expectedMoves));
    }

    char[][] rooksTests1 = {
            {'.', '.', '.', '.', '.'},
            {'.', 'r', '.', 'p', '.'},
            {'.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.'},
            {'.', 'P', '.', 'R', '.'},
            {'.', '.', '.', '.', '.'}
    };

    @Test
    public void rooksTest1White() {
        Board testBoard3 = new Board(rooksTests1, 'W', 10);
        Square wRookSquare = new Square(4, 3);
        Square wPawnSquare = new Square(4, 1);
        ArrayList<Move> outputMoves = testBoard3.moves();
        ArrayList<Move> expectedMoves = new ArrayList<>();
        //where can the pawn go
        expectedMoves.add(new Move(wPawnSquare, new Square(3, 1)));
        //where can the rook go: left
        expectedMoves.add(new Move(wRookSquare, new Square(4, 2)));
        //where can the rook go: right
        expectedMoves.add(new Move(wRookSquare, new Square(4, 4)));
        //where can the rook go: up
        expectedMoves.add(new Move(wRookSquare, new Square(3, 3)));
        expectedMoves.add(new Move(wRookSquare, new Square(2, 3)));
        expectedMoves.add(new Move(wRookSquare, new Square(1, 3)));
        //where can the rook go: down
        expectedMoves.add(new Move(wRookSquare, new Square(5, 3)));
        assertTrue(expectedMoves.containsAll(outputMoves));
        assertTrue(outputMoves.containsAll(expectedMoves));
    }

    @Test
    public void rooksTest1Black() {
        Board testBoard3 = new Board(rooksTests1, 'B', 10);
        Square bRookSquare = new Square(1, 1);
        Square bPawnSquare = new Square(1, 3);
        ArrayList<Move> outputMoves = testBoard3.moves();
        ArrayList<Move> expectedMoves = new ArrayList<>();
        //where can the pawn go
        expectedMoves.add(new Move(bPawnSquare, new Square(2, 3)));
        //where can the rook go: left
        expectedMoves.add(new Move(bRookSquare, new Square(1, 0)));
        //where can the rook go: right
        expectedMoves.add(new Move(bRookSquare, new Square(1, 2)));
        //where can the rook go: up
        expectedMoves.add(new Move(bRookSquare, new Square(0, 1)));
        //where can the rook go: down
        expectedMoves.add(new Move(bRookSquare, new Square(2, 1)));
        expectedMoves.add(new Move(bRookSquare, new Square(3, 1)));
        expectedMoves.add(new Move(bRookSquare, new Square(4, 1)));
        assertTrue(expectedMoves.containsAll(outputMoves));
        assertTrue(outputMoves.containsAll(expectedMoves));
    }

    char[][] bishopsTest1 = {
            {'.', 'p', '.', '.', '.'},
            {'P', 'b', '.', '.', '.'},
            {'.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.'},
            {'.', '.', '.', 'B', 'p'},
            {'.', '.', '.', 'P', '.'}
    };

    @Test
    public void bishopsTest1White() {
        Board testBoard = new Board(bishopsTest1, 'W', 10);
        Square wBishopSquare = new Square(4, 3);
        Square wPawnSquare1 = new Square(1, 0);
        Square wPawnSquare2 = new Square(5, 3);
        ArrayList<Move> outputMoves = testBoard.moves();
        ArrayList<Move> expectedMoves = new ArrayList<>();
        //where can the pawns go
        expectedMoves.add(new Move(wPawnSquare1, new Square(0, 0)));
        expectedMoves.add(new Move(wPawnSquare1, new Square(0, 1)));
        expectedMoves.add(new Move(wPawnSquare2, new Square(4, 4)));
        //where can the bishop go: up and left
        expectedMoves.add(new Move(wBishopSquare, new Square(3, 2)));//
        expectedMoves.add(new Move(wBishopSquare, new Square(2, 1)));//
        //where can the bishop go: up and right
        expectedMoves.add(new Move(wBishopSquare, new Square(3, 4))); //
        //where can the bishop go: down and left
        expectedMoves.add(new Move(wBishopSquare, new Square(5, 4)));//
        //where can the bishop go: down and right
        expectedMoves.add(new Move(wBishopSquare, new Square (5, 2)));//
        //where can the bishop go: up and left
        expectedMoves.add(new Move(wBishopSquare, new Square(3, 3))); //
        expectedMoves.add(new Move(wBishopSquare, new Square(4, 2))); //
        assertTrue(expectedMoves.containsAll(outputMoves));
        assertTrue(outputMoves.containsAll(expectedMoves));
    }

    @Test
    public void bishopsTest1Black() {
        Board testBoard = new Board(bishopsTest1, 'B', 10);
        Square bBishopSquare = new Square(1, 1);
        Square bPawnSquare1 = new Square(4, 4);
        Square bPawnSquare2 = new Square(0, 1);
        ArrayList<Move> outputMoves = testBoard.moves();
        ArrayList<Move> expectedMoves = new ArrayList<>();
        expectedMoves.add(new Move(bPawnSquare1, new Square(5, 4)));
        expectedMoves.add(new Move(bPawnSquare1, new Square(5, 3)));
        expectedMoves.add(new Move(bPawnSquare2, new Square(1, 0)));
        expectedMoves.add(new Move(bBishopSquare, new Square(0, 0)));
        expectedMoves.add(new Move(bBishopSquare, new Square(0, 2)));
        expectedMoves.add(new Move(bBishopSquare, new Square(2, 0)));
        expectedMoves.add(new Move(bBishopSquare, new Square(2, 2)));
        expectedMoves.add(new Move(bBishopSquare, new Square(3, 3)));
        expectedMoves.add(new Move(bBishopSquare, new Square(1, 2)));
        expectedMoves.add(new Move(bBishopSquare, new Square(2, 1)));
//        for (int i = 0; i < outputMoves.size(); i++) {
//            System.out.println(outputMoves.get(i).toSquare.xCord + " " + outputMoves.get(i).toSquare.yCord);
//        }
        assertTrue(expectedMoves.containsAll(outputMoves));
        assertTrue(outputMoves.containsAll(expectedMoves));
    }

    char[][] knightsTest1 = {
            {'.', '.', '.', '.', '.'},
            {'.', 'n', 'P', '.', 'p'},
            {'.', '.', '.', '.', '.'},
            {'p', '.', 'P', '.', '.'},
            {'.', '.', '.', 'N', '.'},
            {'.', '.', '.', '.', '.'}
    };

    @Test
    public void knightsTest1White() {
        Board testBoard = new Board(knightsTest1, 'W', 10);
        Square wKnightSquare = new Square(4, 3);
        Square wPawnSquare1 = new Square(1, 2);
        Square wPawnSquare2 = new Square(3, 2);
        ArrayList<Move> outputMoves = testBoard.moves();
        ArrayList<Move> expectedMoves = new ArrayList<>();
        //where can the top pawn go
        expectedMoves.add(new Move(wPawnSquare1, new Square(0, 2)));
        //where can the bottom pawn go
        expectedMoves.add(new Move(wPawnSquare2, new Square (2, 2)));
        //where can the knight go: up and left/right
        expectedMoves.add(new Move(wKnightSquare, new Square(2, 2)));
        expectedMoves.add(new Move(wKnightSquare, new Square(2, 4)));
        //where can knight go: left and up/down
        expectedMoves.add(new Move(wKnightSquare, new Square(3, 1))); //
        expectedMoves.add(new Move(wKnightSquare, new Square(5, 1)));
//        for (int i = 0; i < outputMoves.size(); i++) {
//            System.out.println(outputMoves.get(i).toSquare.xCord + " " + outputMoves.get(i).toSquare.yCord);
//        }
        assertTrue(expectedMoves.containsAll(outputMoves));
        assertTrue(outputMoves.containsAll(expectedMoves));
    }

    @Test
    public void knightsTest1Black() {
        Board testBoard = new Board(knightsTest1, 'B', 10);
        Square bKnightSquare = new Square(1, 1);
        Square bPawnSquare1 = new Square(1, 4);
        Square bPawnSquare2 = new Square(3, 0);
        ArrayList<Move> outputMoves = testBoard.moves();
        ArrayList<Move> expectedMoves = new ArrayList<>();
        //upper pawn moves first
        expectedMoves.add(new Move(bPawnSquare1, new Square(2, 4)));
        //lower pawn moves second
        expectedMoves.add(new Move(bPawnSquare2, new Square(4, 0)));
        //knight moves
        expectedMoves.add(new Move(bKnightSquare, new Square(0, 3)));
        expectedMoves.add(new Move(bKnightSquare, new Square(2, 3)));
        expectedMoves.add(new Move(bKnightSquare, new Square(3, 2)));
        assertTrue(expectedMoves.containsAll(outputMoves));
        assertTrue(outputMoves.containsAll(expectedMoves));
    }

    char[][] pawnTest = {
            {'.', '.', '.', '.', '.'},
            {'P', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.'},
            {'p', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.'}
    };

    @Test
    public void testQueenPromotionBlack() {
        Board testBoard = new Board(pawnTest, 'B', 10);
        ArrayList<Move> moves = testBoard.moves();
        boolean move = testBoard.makeMove(moves.get(0));
        assertTrue(move);
        testBoard.undoPawnPromotionMove(moves.get(0), '.');
        assertTrue(pawnTest.equals(testBoard.board));
    }

    @Test
    public void testQueenPromotionWhite() {
        Board testBoard = new Board(pawnTest, 'W', 10);
        ArrayList<Move> moves = testBoard.moves();
        boolean move = testBoard.makeMove(moves.get(0));
        assertTrue(move);
        testBoard.undoPawnPromotionMove(moves.get(0), '.');
        assertTrue(pawnTest.equals(testBoard.board));
    }

    char[][] nullProblem = {
            {'k', 'q', '.', 'n', 'r'},
            {'p', '.', 'p', 'b', 'p'},
            {'P', 'p', '.', '.', '.'},
            {'.', 'P', 'N', 'P', '.'},
            {'.', '.', '.', '.', 'P'},
            {'R', '.', 'B', 'Q', 'K'}
    };

    @Test
    public void testNullProblem() {
        Board testBoard = new Board(nullProblem, 'B', 9);
        ArrayList<Move> moves = testBoard.moves();

//        for (Move move : moves) {
//            char clobberedSquare = nullProblem[move.getToSquare().getxCord()][move.getToSquare().getyCord()];
//            boolean isMovePawnPromotion = testBoard.makeMove(move);
//            if (isMovePawnPromotion) {
//                testBoard.undoPawnPromotionMove(move, clobberedSquare);
//            } else {
//                testBoard.undoMove(move, clobberedSquare);
//            }
//        }

        while (testBoard.isKingStillOnBoard()) {
            testBoard.makeRandomMove();
        }
    }


}
