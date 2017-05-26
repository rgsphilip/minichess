import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by rPhilip on 4/28/17.
 */
public class Runner {
    public static void main(String[] args) throws IOException {
        //need to define option flags
        //--help -> lists all args
        //--playself <option>
        //--playopp <color> -> program plays as <color> against an opponent
        // POSSIBLE PLAYERS
        //random -> player uses random search
        //POSSIBLE PLAYERS TO BE IMPLEMENTED
        //negamax:<depth> -> player uses negamax to a specified depth
        //negamaxAb:<depth> -> player uses negamax with alphabeta pruning to a certain depth
        //negamaxTTableAb:<depth> -> player uses negamax with alphabeta + a t-table
        //--playIMCS mochi mochi accept 15658 B 6

        if (Objects.equals(args[0], "--help")) {
            System.out.println("help coming soon!");
            System.exit(0);
        }

        if (Objects.equals(args[0], "--playself")) {
            //program is going to play itself
            switch (args[1]) {
                case "random":
                    Board board = new Board();
                    board.printBoard();
                    while(true) {
                        String str = board.makeRandomMove();
                        System.out.println(str);
                        board.printBoard();
                    }
                case "negamax":
                    int depth = Integer.parseInt(args[2]);
                    Board boardNegaMax = new Board();
                    boardNegaMax.printBoard();
                    int increment = 0;
                    while (increment < 81) {
                        String move = boardNegaMax.negaMaxPlayer(depth);
                        //System.out.println(str);
                        System.out.println(move);
                        boardNegaMax.printBoard();
                        increment++;
                    }
                    break;
                case "alphaBeta":
                    int depthAB = Integer.parseInt(args[2]);
                    Board boardAB = new Board();
                    boardAB.printBoard();
                    int incrementAB = 0;
                    while (incrementAB < 81) {
                        String move = boardAB.alphaBetaPlayer(depthAB);
                        //System.out.println(str);
                        System.out.println(move);
                        boardAB.printBoard();
                        incrementAB++;
                    }
                    break;
            }

        }

        if (Objects.equals(args[0], "--playopp")) {
            Scanner in  = new Scanner(System.in);
            //program is going to play opponent
            switch(args[1]) {
                case "alphaBeta":

                    int depthAB = Integer.parseInt(args[2]);
                    //set the player color
                    Board.Color color = Board.Color.WHITE;
                    if (Objects.equals(args[3], "black")) {
                        color = Board.Color.BLACK;
                    }
                    Board boardAB = new Board(color);
                    boardAB.printBoard();
                    //if color == white, then this program goes first.
                    if (color == Board.Color.WHITE) {
                        //make a move, report move as a string
                        while (true) {
                            String move = boardAB.alphaBetaPlayer(depthAB);
                            System.out.println(move);
                            boardAB.printBoard();
                            String oppMove = in.nextLine();
                            boardAB.makeMove(boardAB.strToMove(oppMove));
                            System.out.println(boardAB.strToMove(oppMove));
                            boardAB.printBoard();
                        }
                        //then... wait until string input has been received for opponent move

                    } else {
                        while (true) {
                            String oppMove = in.nextLine();
                            boardAB.makeMove(boardAB.strToMove(oppMove));
                            boardAB.printBoard();
                            String move = boardAB.alphaBetaPlayer(depthAB);
                            boardAB.printBoard();
                            System.out.println(move);
                        }
                    }
                    //else, opponent goes first.

            }

        }
        if (Objects.equals(args[0], "--playIMCS")) {

            //--playIMCS <username> <password> accept <gamenumber> <color>
            String username = args[1];
            String password = args[2];
            Client client = new Client("imcs.svcs.cs.pdx.edu", 3589);
            client.login(username, password);
            if (Objects.equals(args[3], "accept")) {
                client.accept(args[4]);
            }
            Board.Color myColor = Board.Color.BLACK;

            if (Objects.equals(args[5], "W")) {
                myColor = Board.Color.WHITE;
            }

            int depth = Integer.parseInt(args[6]);
            Board clientBoard = new Board(myColor);
            if (myColor == Board.Color.BLACK) {
                //get move, then make move
                while (clientBoard.isKingStillOnBoard()) {
                    String opMove = client.getMove();
                    clientBoard.makeMove(clientBoard.strToMove(opMove));
                    clientBoard.printBoard();
                    if (clientBoard.isKingStillOnBoard()) {
                        //my king is still on the board
                        String move = clientBoard.alphaBetaPlayer(depth);
                        client.sendMove(move);
                        clientBoard.printBoard();
                    } else {
                        //my king is gone :(
                        System.out.println("mochi lost :(");
                        client.close();
                        System.exit(0);
                    }
                }
                System.out.println("mochi won! :)");
                client.close();
                System.exit(0);

            } else {
                clientBoard.printBoard();
                while (clientBoard.isKingStillOnBoard()) {
                    String move = clientBoard.alphaBetaPlayer(depth);
                    System.out.println(move);
                    client.sendMove(move);
                    clientBoard.printBoard();
                    if (clientBoard.isKingStillOnBoard()) {
                        //I haven't taken the opponent's king
                        String opMove = client.getMove();
                        System.out.println(opMove);
                        clientBoard.makeMove(clientBoard.strToMove(opMove));
                        clientBoard.printBoard();
                    } else {
                        //I have taken the opponent's king
                        System.out.println("mochi won! :)");
                        client.close();
                        System.exit(0);
                    }

                }
                System.out.println("mochi lost :(");
                client.close();
                System.exit(0);
            }



        }


    }

}
