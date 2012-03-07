package player;

public class Board {
	public final static int EMPTY = -1;
	public final static int BLACK = 0;
	public final static int WHITE = 1;

	protected int[][] gameBoard = new int[8][8];
	protected int whiteAddPieces = 10;   //Machine player's pieces
	protected int blackAddPieces = 10;

    /**
     * Sets each item on the board to EMPTY.
     */
	public Board() {
		for (int i = 0; i<8; i++) {
			for (int y =0; y<8; y++) {
				gameBoard[i][y] = EMPTY;
			}
		}
	}
    
    /**
     * makeMove takes a Move m and int player and changes "this" board accordingly.
     * Assumes Move m is a legal move.
     * If Move m is an ADD Move, and the player of playerColor is out of pieces, the method does nothing.
     * Else, makeMove adds the piece to the board and decrements the number of pieces of playerColor accordingly.
     * @param m is the Move object to be applied to "this board"
     * @playerColor is the color of the player making the move
     */
	protected void makeMove(Move m, int playerColor) {
		if (m.moveKind == Move.ADD) {
			if ((playerColor == BLACK) && (blackAddPieces <= 0) || (playerColor == WHITE) && (whiteAddPieces <= 0)) {
				return;
			}
			else {
				gameBoard[m.x1][m.y1] = playerColor;
				if (playerColor == WHITE) {
					whiteAddPieces--;
				}
				else if (playerColor == BLACK){
					blackAddPieces--;
				}
			}
		}
		else if (m.moveKind == Move.STEP) {
			gameBoard[m.x2][m.y2] = EMPTY;  //Set old positions to empty
			gameBoard[m.x1][m.y1] = playerColor;  //Set new positions
		}
	}

    /**
     * undoMove takes a Move m and undoes the Move m on "this" game board
     * Assumes Move m is a legal.
     * If Move m is an ADD Move, undoMove removes the piece from the board and increments the number of pieces of playerColor accordingly.
     * @param m is the Move object you want to undo on "this board"
     * @param playerColor is the color of the player who made the move
     */
	protected void undoMove(Move m, int playerColor) {
		if (m.moveKind == Move.ADD) {
			gameBoard[m.x1][m.y1] = EMPTY;
			if (playerColor == WHITE) {
				whiteAddPieces++;
			}
			else if (playerColor == BLACK) {
				blackAddPieces++;
			}
		}
		else if (m.moveKind == Move.STEP) {
			gameBoard[m.x1][m.x1] = EMPTY;
			gameBoard[m.x2][m.y2] = playerColor;
		}
	}

//	protected DList listLegalMoves(int x, int y, int player) {
//	}


    /**
     * isNetwork returns true if "this" board contains a network for the "player"
     * and false if otherwise.
     *
     * @param player the player to check for networks.
     * @return true if "this board has a network for "player," false if otherwise.
     */
    protected boolean isNetwork(int player) {
	//Work in Progress
    }

    /**
     * listConnections returns a DList containing all the pieces with connections
     * to the piece in coordinates (x, y) on "this" board.  The DList contains 
     * a list of two element arrays containing the x and y coord of the pieces.
     * 
     * @param x the x-coordinate of the piece.
     * @param y the y-coordinate of the piece.
     * @return a DList with all the pieces that make connections with the piece.
     */

    protected DList listConnections(int x, int y) {
	//Work in Progress
    }
}