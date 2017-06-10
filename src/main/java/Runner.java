import java.io.IOException;
import java.util.Objects;

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
        //--playIMCS mochi mochi accept 15658 B AB 6

        if (Objects.equals(args[0], "--help")) {
            System.out.println("You can have the computer play itself with the following player options:");
            System.out.println("random");
            System.out.println("negamax <depth>");
            System.out.println("alphaBeta <depth>");
            System.out.println("IDAB");
            System.out.println("IDABTT");
            System.out.println("To play single player, the command is \"--playself <white player option> <black player option>\"");
            System.out.println();
            System.out.println("You can play on IMCS against the following players:");
            System.out.println("alphaBeta <depth>");
            System.out.println("IDAB");
            System.out.println("IDABTT");
            System.out.println("To accept on IMCS, the command is: " +
                    "\n\"--playIMCS <username> <password> accept <game number> <color> <player> <optional depth>\"");
            System.out.println("To offer on IMCS, the command is: " +
                    "\n\"--playIMCS <username> <password> offer <color> <player> <optional depth>\"");
            System.exit(0);
        }

        //--playself <player1>:<optional param> <player2>:<optional param>

        if (Objects.equals(args[0], "--playself")) {
            Board board = new Board();
            board.printBoard();
            //program is going to play itself
            switch (args[1]) {
                case "random":
                    while(board.isKingStillOnBoard()) {
                        makeRandomMove(board);
                        if (board.isKingStillOnBoard()) {
                            if (args[2].equals("random")) {
                                makeRandomMove(board);
                            }
                            if (args[2].equals("negamax")) {
                                makeNegamaxMove(board, Integer.parseInt(args[3]));
                            }
                            if (args[2].equals("alphaBeta")) {
                                makeAlphaBetaMove(board, Integer.parseInt(args[3]));
                            }
                            if (args[2].equals("IDAB")) {
                                makeIDABMove(board);
                            }
                            if (args[2].equals("IDABTT")) {
                                makeIDABMoveTTable(board);
                            }
                        } else {
                            System.out.println("White wins");
                            System.exit(0);
                        }
                    }
                    System.out.println("Black wins");
                    System.exit(0);
                case "negamax":
                    int depthW = Integer.parseInt(args[2]);

                    while(board.isKingStillOnBoard()) {
                        makeNegamaxMove(board, depthW);
                        if (board.isKingStillOnBoard()) {
                            if (args[3].equals("random")) {
                                makeRandomMove(board);
                            }
                            if (args[3].equals("negamax")) {
                                makeNegamaxMove(board, Integer.parseInt(args[4]));
                            }
                            if (args[3].equals("alphaBeta")) {
                                makeAlphaBetaMove(board, Integer.parseInt(args[4]));
                            }
                            if (args[3].equals("IDAB")) {
                                makeIDABMove(board);
                            }
                            if (args[3].equals("IDABTT")) {
                                makeIDABMoveTTable(board);
                            }

                        } else {
                            System.out.println("White wins");
                            System.exit(0);
                        }
                    }
                    System.out.println("Black wins");
                    System.exit(0);
                case "alphaBeta":
                    int depthWh = Integer.parseInt(args[2]);

                    while(board.isKingStillOnBoard()) {
                        makeAlphaBetaMove(board, depthWh);
                        if (board.isKingStillOnBoard()) {
                            if (args[3].equals("random")) {
                                makeRandomMove(board);
                            }
                            if (args[3].equals("negamax")) {
                                makeNegamaxMove(board, Integer.parseInt(args[4]));
                            }
                            if (args[3].equals("alphaBeta")) {
                                makeAlphaBetaMove(board, Integer.parseInt(args[4]));
                            }
                            if (args[3].equals("IDAB")) {
                                makeIDABMove(board);
                            }
                            if (args[3].equals("IDABTT")) {
                                makeIDABMoveTTable(board);
                            }
                        } else {
                            System.out.println("White wins");
                            System.exit(0);
                        }
                    }
                    System.out.println("Black wins");
                    System.exit(0);
                case "IDAB":
                    while(board.isKingStillOnBoard()) {
                        makeIDABMove(board);
                        if (board.isKingStillOnBoard()) {
                            if (args[2].equals("random")) {
                                makeRandomMove(board);
                            }
                            if (args[2].equals("IDAB")) {
                                makeIDABMove(board);
                            }
                            if (args[2].equals("negamax")) {
                                makeNegamaxMove(board, Integer.parseInt(args[3]));
                            }
                            if (args[2].equals("alphaBeta")) {
                                makeAlphaBetaMove(board, Integer.parseInt(args[3]));
                            }
                            if (args[2].equals("IDABTT")) {
                                makeIDABMoveTTable(board);
                            }
                        } else {
                            System.out.println("White wins");
                            System.exit(0);
                        }
                    }
                    System.out.println("Black wins");
                    System.exit(0);
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
                Board.Color myColor = Board.Color.BLACK;

                if (Objects.equals(args[5], "W")) {
                    myColor = Board.Color.WHITE;
                }

                String player = args[6];
                if (player.equals("AB")) {

                    int depth = Integer.parseInt(args[7]);
                    Board clientBoard = new Board(myColor);
                    if (myColor == Board.Color.BLACK) {
                        //get move, then make move
                        while (clientBoard.isKingStillOnBoard()) {
                            System.out.println("**** OPPONENT MOVE ****");
                            String opMove = client.getMove();
                            System.out.println(opMove);
                            clientBoard.makeMove(clientBoard.strToMove(opMove));
                            clientBoard.printBoard();
                            if (clientBoard.isKingStillOnBoard()) {
                                //my king is still on the board
                                System.out.println("**** MOCHI MOVE ****");
                                String move = clientBoard.alphaBetaPlayer(depth);
                                System.out.println(move);
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
                            System.out.println("**** MOCHI MOVE ****");
                            String move = clientBoard.alphaBetaPlayer(depth);
                            System.out.println(move);
                            client.sendMove(move);
                            clientBoard.printBoard();
                            if (clientBoard.isKingStillOnBoard()) {
                                System.out.println("**** OPPONENT MOVE ****");
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
                } else if (player.equals("IDAB")) {
                    Board clientBoard = new Board(myColor);
                    if (myColor == Board.Color.BLACK) {
                        //get move, then make move
                        while (clientBoard.isKingStillOnBoard()) {
                            System.out.println("**** OPPONENT MOVE ****");
                            String opMove = client.getMove();
                            System.out.println(opMove);
                            clientBoard.makeMove(clientBoard.strToMove(opMove));
                            clientBoard.printBoard();
                            if (clientBoard.isKingStillOnBoard()) {
                                //my king is still on the board
                                System.out.println("**** MOCHI MOVE ****");
                                String move = clientBoard.iterativelyDeepeningABPlayer();
                                System.out.println(move);
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
                            System.out.println("**** MOCHI MOVE ****");
                            String move = clientBoard.iterativelyDeepeningABPlayer();
                            System.out.println(move);
                            client.sendMove(move);
                            clientBoard.printBoard();
                            if (clientBoard.isKingStillOnBoard()) {
                                System.out.println("**** OPPONENT MOVE ****");
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
                } else if (player.equals("IDABTT")) {
                    Board clientBoard = new Board(myColor);
                    if (myColor == Board.Color.BLACK) {
                        //get move, then make move
                        while (clientBoard.isKingStillOnBoard()) {
                            System.out.println("**** OPPONENT MOVE ****");
                            String opMove = client.getMove();
                            System.out.println(opMove);
                            clientBoard.makeMove(clientBoard.strToMove(opMove));
                            clientBoard.printBoard();
                            if (clientBoard.isKingStillOnBoard()) {
                                //my king is still on the board
                                System.out.println("**** MOCHI MOVE ****");
                                String move = clientBoard.iterativelyDeepeningABPlayerTTable();
                                System.out.println(move);
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
                            System.out.println("**** MOCHI MOVE ****");
                            String move = clientBoard.iterativelyDeepeningABPlayerTTable();
                            System.out.println(move);
                            client.sendMove(move);
                            clientBoard.printBoard();
                            if (clientBoard.isKingStillOnBoard()) {
                                System.out.println("**** OPPONENT MOVE ****");
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

            } else if (args[3].equals("offer")){
                char[] color = args[4].toCharArray();
                System.out.println(color[0]);
                client.offerGameAndWait(color[0]);

                Board.Color myColor = Board.Color.BLACK;

                if (Objects.equals(args[4], "W")) {
                    myColor = Board.Color.WHITE;
                }

                String player = args[5];
                if (player.equals("AB")) {
                    int depth = Integer.parseInt(args[6]);
                    Board clientBoard = new Board();
                    if (myColor == Board.Color.BLACK) {
                        //get move, then make move
                        while (clientBoard.isKingStillOnBoard()) {
                            System.out.println("**** OPPONENT MOVE ****");
                            String opMove = client.getMove();
                            clientBoard.makeMove(clientBoard.strToMove(opMove));
                            clientBoard.printBoard();
                            if (clientBoard.isKingStillOnBoard()) {
                                //my king is still on the board
                                System.out.println("**** MOCHI MOVE *****");
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
                } else if (player.equals("IDAB")) {
                    Board clientBoard = new Board();
                    if (myColor == Board.Color.BLACK) {
                        //get move, then make move
                        while (clientBoard.isKingStillOnBoard()) {
                            System.out.println("**** OPPONENT MOVE ****");
                            String opMove = client.getMove();
                            clientBoard.makeMove(clientBoard.strToMove(opMove));
                            clientBoard.printBoard();
                            if (clientBoard.isKingStillOnBoard()) {
                                //my king is still on the board
                                System.out.println("**** MOCHI MOVE *****");
                                String move = clientBoard.iterativelyDeepeningABPlayer();
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
                            String move = clientBoard.iterativelyDeepeningABPlayer();
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
    }

    static void makeNegamaxMove(Board board, int depth) {
        String move = board.negaMaxPlayer(depth);
        System.out.println(move);
        board.printBoard();
    }

    static void makeAlphaBetaMove(Board board, int depth) {
        String move = board.alphaBetaPlayer(depth);
        System.out.println(move);
        board.printBoard();
    }

    static void makeRandomMove(Board board) {
        String move = board.makeRandomMove();
        System.out.println(move);
        board.printBoard();
    }
    static void makeIDABMove(Board board) {
        String move = board.iterativelyDeepeningABPlayer();
        System.out.println(move);
        board.printBoard();
    }

    static void makeIDABMoveTTable(Board board) {
        String move = board.iterativelyDeepeningABPlayerTTable();
        System.out.println(move);
        board.printBoard();
    }

}
