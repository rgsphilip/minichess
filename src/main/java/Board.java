import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by rPhilip on 4/24/17.
 */
public class Board {
    enum Color {WHITE, BLACK}

    //board to initialize game if we are not given a board.
    char[][] board = {
            {'k', 'q', 'b', 'n', 'r'},
            {'p', 'p', 'p', 'p', 'p'},
            {'.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.'},
            {'P', 'P', 'P', 'P', 'P'},
            {'R', 'N', 'B', 'Q', 'K'}
    };
    Color onMove;
    int numRows = 6;
    int numColumns = 5;
    int numMoves = 1;
    int maxNumMoves = 40;
    Color myColor;

    //global var for iterative deepening
    int ticker = 0;

    boolean playOpponent = false;
    //ArrayList<Piece> pieceList = new ArrayList<Piece>();
    ArrayList<Piece> blackPieceList = new ArrayList<>();
    ArrayList<Piece> whitePieceList = new ArrayList<>();
    private Random randomGenerator;

    //initialize game as white
    public Board() {
        onMove = Color.WHITE;
        randomGenerator = new Random();
        initializePieceList();
    }

    public Board(Color myColor) {
        this.myColor = myColor;
        onMove = Color.WHITE;
        randomGenerator = new Random();
        initializePieceList();
        playOpponent = true;
    }

    //receive a board from someone else
    public Board(char[][] board, char onMove, int numMoves) {
        this.board = board;
        if (onMove == 'W') {
            this.onMove = Color.WHITE;
        } else { //TODO: probably need to create some sort of error throw here rather than simple else
            this.onMove = Color.BLACK;
        }
        this.numMoves = numMoves;
        initializePieceList();
        randomGenerator = new Random();
    }

    //Initialize the pieces on the board for both white and black
    private void initializePieceList() {
        char minLetter = 'A';
        char maxLetter = 'Z';
        for(int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (board[i][j] > minLetter && board[i][j] < maxLetter) {
                    whitePieceList.add(new Piece(board[i][j], i, j));
                }
            }
        }
        minLetter = 'a';
        maxLetter = 'z';
        for(int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (board[i][j] > minLetter && board[i][j] < maxLetter) {
                    blackPieceList.add(new Piece(board[i][j], i, j));
                }
            }
        }
    }

    //checkPieceList will be performed each time we get the board back from the opponent, because it's possible
    //that they have taken one of our pieces.
    private void checkPieceList() {
        ArrayList<Piece> pieceList = null;
        if (onMove == Color.WHITE) {
            pieceList = whitePieceList;
        } else if (onMove == Color.BLACK) {
            pieceList = blackPieceList;
        }
        assert (pieceList != null);
        int numChanges = 0;
        int ixToRemove = -1;
        for(int i = 0; i < pieceList.size(); i++) {
            Piece piece = pieceList.get(i);
            int xCord = piece.getxCord();
            int yCord = piece.getyCord();
            char pieceType = piece.getPieceType();
            if (board[xCord][yCord] != pieceType) {
                ixToRemove = i;
                numChanges++;
            }
        }
        //there should only ever 0 or 1 changes, from the opponent taking one piece.
        assert(numChanges < 2 && numChanges > -1);
        if (numChanges == 1) {
            pieceList.remove(ixToRemove);
        }
    }



    private void announceIfThereIsAWinner() {
        if (!isKingStillOnBoard()) {
            System.out.println("The game is over!");
            if (onMove == Color.WHITE) {
                System.out.println("Black won!");
            } else {
                System.out.println("White won!");
            }
            System.exit(0);
        }
        if (isKingStillOnBoard() && (numMoves > maxNumMoves)) {
            System.out.println("It's a draw!");
            System.exit(0);
        }
    }

    public void printBoard() {
        if (onMove == Color.WHITE) {
            System.out.println(numMoves + " W");
        } else {
            System.out.println(numMoves + " B");
        }
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    //Translates a Move into a string representation for communicating to IMCS and human readability
    public String moveToString(Move move) {
        String str = "";
        //fromSquare Y is mapped to first char
        String firstChar = Character.toString((char) ('a' + move.getFromSquare().getyCord()));
        String secondChar = Character.toString(translateCoordinateToChessRow(move.getFromSquare().getxCord()));
        String thirdChar = "-";
        String fourthChar = Character.toString((char) ('a' + move.getToSquare().getyCord()));
        String fifthChar = Character.toString(translateCoordinateToChessRow(move.getToSquare().getxCord()));
        str += firstChar + secondChar + thirdChar + fourthChar + fifthChar;
        return str;
    }

    //Translates a string chess move into a Move representation to be consumed by the player
    public Move strToMove(String str) {
        //a1-a2
        //columns are a - e
        //rows are 1 - 6, 1 being the bottom row
        int fromSquareXCord = tranlateChessRowToCoordinate(str.charAt(1));
        int fromSquareYCord = str.charAt(0) - 'a';
        int toSquareXCord = tranlateChessRowToCoordinate(str.charAt(4));
        int toSquareYCord = str.charAt(3) - 'a';
        return new Move(new Square(fromSquareXCord, fromSquareYCord), new Square(toSquareXCord, toSquareYCord));

    }

    //Annoyingly long helper methods for move -> str and str -> move translation
    private int tranlateChessRowToCoordinate(char chr) {
        int value = 0; //char value 6
        switch (chr){
            case '1':
                value = 5;
                break;
            case '2':
                value = 4;
                break;
            case '3':
                value = 3;
                break;
            case '4':
                value = 2;
                break;
            case '5':
                value = 1;
                break;
        }
        return value;
    }
    //Annoyingly long helper methods for move -> str and str -> move translation
    private char translateCoordinateToChessRow(int i) {
        char c = '6'; //int value 0
        switch (i) {
            case 1:
                c = '5';
                break;
            case 2:
                c = '4';
                break;
            case 3:
                c = '3';
                break;
            case 4:
                c = '2';
                break;
            case 5:
                c = '1';
                break;
        }
        return c;
    }

    ///////////////////////////// TYPES OF PLAYERS ////////////////////////////

    //Random Player
    public String makeRandomMove() {
        announceIfThereIsAWinner();
        ArrayList<Move> moves = moves();
        int randomMoveIx = randomGenerator.nextInt(moves.size());
        Move move = moves.get(randomMoveIx);
        makeMove(move);
        return moveToString(move);
    }

    //Negamax Player
    public String negaMaxPlayer(int depth) {
        int score = -100000;
        int compare = 0;
        ArrayList<Move> moves = moves();
        if (moves.isEmpty()) {
            return "no more moves";
        }
        Move bestMove = null;
        for (Move move : moves) {
            char clobberedSquare = board[move.getToSquare().getxCord()][move.getToSquare().getyCord()];
            boolean isMovePromotingPawn = makeMove(move);
            compare = -negamax(depth-1);
            if (isMovePromotingPawn) {
                undoPawnPromotionMove(move, clobberedSquare);
            } else {
                undoMove(move, clobberedSquare);
            }
            if (compare > score) {
                bestMove = move;
                score = compare;
            }
        }
        makeMove(bestMove);
        return moveToString(bestMove);
    }

    //Recursive Negamax Function
    private int negamax(int depth) {
        ArrayList<Move> moves = moves();
        if (moves.isEmpty() || !isKingStillOnBoard()) {
            return -100000;
        }
        if (depth < 1) {
            return calculateMaterialValue();
        }
        int maxVal = -100000;
        for (Move move : moves) {
            char clobberedSquare = board[move.getToSquare().getxCord()][move.getToSquare().getyCord()];
            boolean isMovePromotionPawn = this.makeMove(move);
            int value;
            if (!isKingStillOnBoard()) {
                this.undoMove(move, clobberedSquare);
                return 100000;
            } else {
                value = -1 * negamax(depth - 1);
            }
            if (isMovePromotionPawn) {
                this.undoPawnPromotionMove(move, clobberedSquare);
            } else {
                this.undoMove(move, clobberedSquare);
            }
            maxVal = Math.max(maxVal, value);
        }
        return maxVal;
    }

    //Alphabeta Player
    public String alphaBetaPlayer(int depth) {
        Move bestMove = null;
        int alpha = -100000;
        int beta = 100000;
        int score = 0;

        blackPieceList.clear();
        whitePieceList.clear();
        initializePieceList();

        ArrayList<Move> moves = moves();
        for (Move move : moves) {
            char clobberedSquare = board[move.getToSquare().getxCord()][move.getToSquare().getyCord()];
            boolean isMovePawnPromotion = makeMove(move);
            score = -1 * alphaBeta(depth - 1, -1 * beta, -1 * alpha);
            if (isMovePawnPromotion) {
                undoPawnPromotionMove(move, clobberedSquare);
            } else {
                undoMove(move, clobberedSquare);
            }

            if (score > alpha) {
                bestMove = move;
                alpha = score;
            }
        }
        if (bestMove == null) {
            int randomMoveIx = randomGenerator.nextInt(moves.size());
            bestMove = moves.get(randomMoveIx);
        }
        makeMove(bestMove);
        return moveToString(bestMove);
    }

    //Alphabeta Recursive Function
    private int alphaBeta(int depth, int alpha, int beta) {
        if (depth == 0) {
            return calculateMaterialValue();
        }
        int score = -100000;
        ArrayList<Move> moves = moves();
        for(Move move : moves) {
            char clobberedSquare = board[move.getToSquare().getxCord()][move.getToSquare().getyCord()];
            boolean isMovePawnPromotion = makeMove(move);
            score = Math.max(score, -1 * alphaBeta(depth - 1, -1 * beta, -1 * alpha));
            if (isMovePawnPromotion) {
                undoPawnPromotionMove(move, clobberedSquare);
            } else {
                undoMove(move, clobberedSquare);
            }
            alpha = Math.max(alpha, score);
            if (alpha >= beta) {
                break;
            }
        }
        return score;
    }

    //Iterative deepener
    //will use AlphaBeta...
    //need to occasionally poll to get time
    public String iterativelyDeepeningABPlayer() {
        blackPieceList.clear();
        whitePieceList.clear();
        initializePieceList();
        ticker = 0;
        Move bestMove = null;
        Move bestMoveLastIteration = null;
        long timeLimit;
        //need most time for midgame
        if (numMoves < 6) {
            //four second for opening
            timeLimit = 2000;
        } else if (numMoves < 25) {
            //eight second for midgame
            timeLimit = 5000;
        } else {
            //seven second for endgame
            timeLimit = 3000;
        }
        long endTime = System.currentTimeMillis() + timeLimit;

        int alpha = -100000;
        int beta = 100000;
        int score = 0;
        int depth = 4;

        //need to wrap this in a while or something --------------------------------------------------
        do {
            ArrayList<Move> moves = moves();
            for (Move move : moves) {
                char clobberedSquare = board[move.getToSquare().getxCord()][move.getToSquare().getyCord()];
                boolean isMovePawnPromotion = makeMove(move);
                score = -1 * iDAlphaBeta(depth - 1, -1 * beta, -1 * alpha, timeLimit);
                if (isMovePawnPromotion) {
                    undoPawnPromotionMove(move, clobberedSquare);
                } else {
                    undoMove(move, clobberedSquare);
                }
                //add in a break for a bad score here?
                if (score > alpha) {
                    bestMove = move;
                    alpha = score;
                }
            }
            if (bestMove == null) {
                int randomMoveIx = randomGenerator.nextInt(moves.size());
                bestMove = moves.get(randomMoveIx);
            }

            System.out.println("depth: " + depth);
            depth++;
            bestMoveLastIteration = bestMove;
            //----------------------------------------------------------------------------------------
        } while (System.currentTimeMillis() < endTime);

        makeMove(bestMoveLastIteration);
        return moveToString(bestMoveLastIteration);
    }

    //ID Alphabeta Recursive Function
    private int iDAlphaBeta(int depth, int alpha, int beta, long timeLimit) {
        ticker++;
        if (ticker > 1000) {
            if (timeLimit > System.currentTimeMillis()) {
                //end search
                return 0;
            }
            ticker = 0;
        }
        if (depth == 0) {
            return calculateMaterialValue();
        }
        int score = -100000;
        ArrayList<Move> moves = moves();
        for(Move move : moves) {
            char clobberedSquare = board[move.getToSquare().getxCord()][move.getToSquare().getyCord()];
            boolean isMovePawnPromotion = makeMove(move);
            score = Math.max(score, -1 * iDAlphaBeta(depth - 1, -1 * beta, -1 * alpha, timeLimit));
            if (isMovePawnPromotion) {
                undoPawnPromotionMove(move, clobberedSquare);
            } else {
                undoMove(move, clobberedSquare);
            }
            alpha = Math.max(alpha, score);
            if (alpha >= beta) {
                break;
            }
        }
        return score;
    }



    ////////////////////////// MOVES /////////////////////////////////
    //make a move
    public boolean makeMove(Move move) {
        Square moveFrom = move.getFromSquare();
        Square moveTo = move.getToSquare();
        boolean isPawnPromoted = false;
        //find piece in piecelist
        ArrayList<Piece> pieceList;
        if (onMove == Color.WHITE) {
            pieceList = whitePieceList;
        } else {
            pieceList = blackPieceList;
            numMoves++;
        }
        Piece piece = null;
        //updating the piece list
        for (int i = 0; i < pieceList.size(); i++) {
            if (pieceList.get(i).getxCord() == moveFrom.getxCord()) {
                if (pieceList.get(i).getyCord() == moveFrom.getyCord()) {
                    piece = pieceList.get(i);
                    pieceList.get(i).setxCord(moveTo.getxCord());
                    pieceList.get(i).setyCord(moveTo.getyCord());

                }
            }
        }
        assert piece != null;
        //pawn promotion
        if (piece.getPieceType() == 'p') { //something wrong here?
            if (piece.getxCord() == 5) {
                piece.setPieceType('q');
                board[moveFrom.getxCord()][moveFrom.getyCord()] = 'q';
                isPawnPromoted = true;
            }
        }
        if (piece.getPieceType() == 'P') {
            if (piece.getxCord() == 0) {
                piece.setPieceType('Q');
                board[moveFrom.getxCord()][moveFrom.getyCord()] = 'Q';
                isPawnPromoted = true;
            }
        }
        //updating the char board
        board[moveTo.getxCord()][moveTo.getyCord()] = board[moveFrom.getxCord()][moveFrom.getyCord()];
        board[moveFrom.getxCord()][moveFrom.getyCord()] = '.';
        if (onMove == Color.WHITE) {
            onMove = Color.BLACK;
        } else {
            onMove = Color.WHITE;
        }
        return isPawnPromoted;
    }

    //undo a move
    public void undoMove(Move move, char clobberedSquare) {
        Square moveFrom = move.getFromSquare();
        Square moveTo = move.getToSquare();

        board[moveFrom.getxCord()][moveFrom.getyCord()] = board[moveTo.getxCord()][moveTo.getyCord()];
        board[moveTo.getxCord()][moveTo.getyCord()] = clobberedSquare;

        blackPieceList.clear();
        whitePieceList.clear();
        initializePieceList();

        if (onMove == Color.WHITE) {
            onMove = Color.BLACK;
            numMoves--;
        } else {
            onMove = Color.WHITE;
        }
    }

    public void undoPawnPromotionMove(Move move, char clobberedSquare) {
        Square moveFrom = move.getFromSquare();
        Square moveTo = move.getToSquare();
        if (onMove == Color.BLACK) {
            board[moveTo.getxCord()][moveTo.getyCord()] = 'P';
        } else {
            board[moveTo.getxCord()][moveTo.getyCord()] = 'p';
        }

        board[moveFrom.getxCord()][moveFrom.getyCord()] = board[moveTo.getxCord()][moveTo.getyCord()];
        board[moveTo.getxCord()][moveTo.getyCord()] = clobberedSquare;

        blackPieceList.clear();
        whitePieceList.clear();
        initializePieceList();

        if (onMove == Color.WHITE) {
            onMove = Color.BLACK;
            numMoves--;
        } else {
            onMove = Color.WHITE;
        }
    }

    //calculating moves
    protected ArrayList<Move> moves() {
        //initialize the list of moves
        ArrayList<Move> moves = new ArrayList<Move>();
        //check to see whether you still have all your pieces
        checkPieceList();
        int pawnMove = 1;
        if (onMove == Color.WHITE) {
            pawnMove = -1;
        }
        ArrayList<Piece> pieceList = null;
        if (onMove == Color.WHITE) {
            pieceList = whitePieceList;
        } else if (onMove == Color.BLACK) {
            pieceList = blackPieceList;
        }
        assert(pieceList != null);
        //generate moves! TODO: add in heuristics for ordering!
        for (Piece piece : pieceList) {
            Square fromSquare = new Square(piece.getxCord(), piece.getyCord());
            int xCord = piece.getxCord();
            int yCord = piece.getyCord();
            switch (Character.toLowerCase(piece.getPieceType())) {
                case 'p':
                    //pawns. color check performed above with pawnMove. TODO: Need to do some sort of check for queen promotion.
                    int moveForward = xCord + pawnMove;
                    //can the pawn move forward?
                    if (isSquareOnTheBoard(moveForward, yCord) && board[moveForward][yCord] == '.') {
                        moves.add(new Move(fromSquare, new Square(moveForward, yCord)));
                    }
                    //can the pawn move diagonally to capture?
                    if (isSquareOnTheBoard(moveForward, yCord - 1) && isSquareOccupiedByOpponent(moveForward, yCord - 1)) {
                        moves.add(new Move(fromSquare, new Square(moveForward, yCord - 1)));
                    }
                    if (isSquareOnTheBoard(moveForward, yCord + 1) && isSquareOccupiedByOpponent(moveForward, yCord + 1)) {
                        moves.add(new Move(fromSquare, new Square(moveForward, yCord + 1)));
                    }
                    break;
                case 'r':
                    rookMover(moves, fromSquare);
                    break;
                case 'n':
                    knightMover(moves, fromSquare);
                    break;
                case 'b':
                    bishopMover(moves, fromSquare);
                    break;
                case 'q':
                    queenMover(moves, fromSquare);
                    break;
                case 'k':
                    kingMover(moves, fromSquare);
                    break;
            }
        }
        if (onMove == Color.BLACK) {
            Collections.reverse(moves);
        }

        return moves;
    }

    //TODO: Make this more sophisticated
    private int calculateMaterialValue() {
        int blackValue = getMaterialValueForSide(blackPieceList);
        int whiteValue = getMaterialValueForSide(whitePieceList);
        if (onMove == Color.BLACK) {
            return blackValue - whiteValue;
        }
        return whiteValue - blackValue;
    }

    int getMaterialValueForSide(ArrayList<Piece> pieceList) {
        int value = 0;
        for (Piece piece: pieceList) {
            switch (Character.toLowerCase(piece.getPieceType())) {
                case 'p':
                    value += 100;
                    break;
                case 'r':
                    value += 500;
                    break;
                case 'n':
                    value += 300;
                    break;
                case 'b':
                    value += 300;
                    break;
                case 'q':
                    value += 900;
                    break;
                case 'k':
                    value += 90000;
                    break;
            }
        }
        return value;
    }

    public boolean isKingStillOnBoard() {
        //are both kings still on the board?
        boolean whiteKing = false;
        boolean blackKing = false;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (board[i][j] == 'k') {
                    blackKing = true;
                }
                if (board[i][j] == 'K') {
                    whiteKing = true;
                }
            }
        }
        return (whiteKing && blackKing);
    }

    private boolean isMyKingStillOnBoard() {
        //i.e., did I lose?
        if (onMove == Color.WHITE) {
            for (Piece piece: whitePieceList) {
                if (piece.getPieceType() == 'K') {
                    return true;
                }
            }
        } else {
            for (Piece piece: blackPieceList) {
                if (piece.getPieceType() == 'k') {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isOppKingStillOnBoard() {
        if (onMove == Color.BLACK) {
            for (Piece piece: whitePieceList) {
                if (piece.getPieceType() == 'K') {
                    return true;
                }
            }
        } else {
            for (Piece piece: blackPieceList) {
                if (piece.getPieceType() == 'k') {
                    return true;
                }
            }
        }
        return false;
    }

    /******************** HELPER METHODS FOR MOVEMENT ********************/
    private boolean moveChecker(ArrayList<Move> moves, Square fromSquare, int xCord, int yCord) {
        if (!isSquareOnTheBoard(xCord, yCord)) {
            return false;
        }
        if (isSquareOccupiedByColleague(xCord, yCord)) {
            return false;
        } else if (isSquareOccupiedByOpponent(xCord, yCord)) {
            moves.add(new Move(fromSquare, new Square(xCord, yCord)));
            return false;
        } else {
            moves.add(new Move(fromSquare, new Square(xCord, yCord)));
        }
        return true;
    }


    private void rookMover(ArrayList<Move> moves, Square fromSquare) {
        int xCord = fromSquare.getxCord();
        int yCord = fromSquare.getyCord();
        //first look left
        int goLeft = -1;
        while (moveChecker(moves, fromSquare, xCord, yCord + goLeft)) {
            goLeft--;
        }
        //then look right
        int goRight = 1;
        while (moveChecker(moves, fromSquare, xCord, yCord + goRight)) {
            goRight++;
        }
        //go up
        int goUp = -1;
        while (moveChecker(moves, fromSquare, xCord + goUp, yCord)) {
            goUp--;
        }
        //go down
        int goDown = 1;
        while (moveChecker(moves, fromSquare, xCord + goDown, yCord)) {
            goDown++;
        }
    }

    private void bishopMover(ArrayList<Move> moves, Square fromSquare) {
        int xCord = fromSquare.getxCord();
        int yCord = fromSquare.getyCord();
        //two cases to consider:
        // 1. moving vertically/horizontally for one space when NOT capturing
        if (isSquareOnTheBoard(xCord, yCord + 1) && board[xCord][yCord + 1] == '.') {
            moves.add(new Move(fromSquare, new Square(xCord, yCord + 1)));
        }
        if (isSquareOnTheBoard(xCord, yCord - 1) && board[xCord][yCord - 1] == '.') {
            moves.add(new Move(fromSquare, new Square(xCord, yCord - 1)));
        }
        if (isSquareOnTheBoard(xCord + 1, yCord) && board[xCord + 1][yCord] == '.') {
            moves.add(new Move(fromSquare, new Square(xCord + 1, yCord)));
        }
        if (isSquareOnTheBoard(xCord - 1, yCord) && board[xCord - 1][yCord] == '.') {
            moves.add(new Move(fromSquare, new Square(xCord - 1, yCord)));
        }
        // 2. moving diagonally with ability to capture
        bishopMoverDiagonal(moves, fromSquare);

    }

    private void bishopMoverDiagonal(ArrayList<Move> moves, Square fromSquare) {
        int xCord = fromSquare.getxCord();
        int yCord = fromSquare.getyCord();
        //go up and right
        int goUp = -1;
        int goRight = 1;
        while (moveChecker(moves, fromSquare, xCord + goUp, yCord + goRight)) {
            goUp--;
            goRight++;
        }
        //go up and left
        goUp = -1;
        int goLeft = -1;
        while (moveChecker(moves, fromSquare, xCord + goUp, yCord + goLeft)) {
            goUp--;
            goLeft--;
        }
        //go down and right
        int goDown = 1;
        goRight = 1;
        while (moveChecker(moves, fromSquare, xCord + goDown, yCord + goRight)) {
            goDown++;
            goRight++;
        }
        //go down and left
        goDown = 1;
        goLeft = -1;
        while (moveChecker(moves, fromSquare, xCord + goDown, yCord + goLeft)) {
            goDown++;
            goLeft--;
        }
    }

    private void knightMover (ArrayList<Move> moves, Square fromSquare) {
        int xCord = fromSquare.getxCord();
        int yCord = fromSquare.getyCord();
        //up and to left/right
        moveChecker(moves, fromSquare, xCord - 2, yCord + 1);
        moveChecker(moves, fromSquare, xCord - 2, yCord - 1);
        //down and to the left/right
        moveChecker(moves, fromSquare, xCord + 2, yCord + 1);
        moveChecker(moves, fromSquare, xCord + 2, yCord - 1);
        //left and up/down
        moveChecker(moves, fromSquare, xCord + 1, yCord - 2);
        moveChecker(moves, fromSquare, xCord - 1, yCord - 2);
        //right and up/down
        moveChecker(moves, fromSquare, xCord + 1, yCord + 2);
        moveChecker(moves, fromSquare, xCord - 1, yCord + 2);
    }

    private void queenMover (ArrayList<Move> moves, Square fromSquare) {
        rookMover(moves, fromSquare);
        bishopMoverDiagonal(moves, fromSquare);
    }

    private void kingMover (ArrayList <Move> moves, Square fromSquare) {
        int xCord = fromSquare.getxCord();
        int yCord = fromSquare.getyCord();
        //go up
        moveChecker(moves, fromSquare, xCord - 1, yCord);
        //go down
        moveChecker(moves, fromSquare, xCord + 1, yCord);
        //go left
        moveChecker(moves, fromSquare, xCord, yCord + 1);
        //go right
        moveChecker(moves, fromSquare, xCord, yCord - 1);
        //go up and left
        moveChecker(moves, fromSquare, xCord - 1, yCord - 1);
        //go up and right
        moveChecker(moves, fromSquare, xCord - 1, yCord + 1);
        //go down and left
        moveChecker(moves, fromSquare, xCord + 1, yCord - 1);
        //go down and right
        moveChecker(moves, fromSquare, xCord + 1, yCord + 1);
    }

    /******************** HELPER METHODS FOR BOARD EVALUATION **********************/
    private boolean isSquareOnTheBoard(int xCord, int yCord) {
        if (xCord > -1 && xCord < numRows) {
            if (yCord > -1 && yCord < numColumns) {
                return true;
            }
        }
        return false;
    }

    private boolean isSquareOccupiedByOpponent(int xCord, int yCord) {
        char minLetter = 'A';
        char maxLetter = 'Z';
        if (onMove == Color.WHITE) {
            minLetter = 'a';
            maxLetter = 'z';
        }
        if (board[xCord][yCord] < maxLetter && board[xCord][yCord] > minLetter) {
            return true;
        }
        return false;
    }

    private boolean isSquareOccupiedByColleague(int xCord, int yCord) {
        char minLetter = 'A';
        char maxLetter = 'Z';
        if (onMove == Color.BLACK) {
            minLetter = 'a';
            maxLetter = 'z';
        }
        if (board[xCord][yCord] < maxLetter && board[xCord][yCord] > minLetter) {
            return true;
        }
        return false;
    }




}
