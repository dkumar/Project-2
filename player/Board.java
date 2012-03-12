package player;
import list.*;

public class Board {
	public final static int EMPTY = -1;
	public final static int BLACK = 0;
	public final static int WHITE = 1;

	private int[][] gameBoard = new int[8][8];
	protected int whiteAddPieces = 10;   //Machine player's pieces
	protected int blackAddPieces = 10;

    /**
     * Sets each item on the board to Board.EMPTY.
     */
	public Board() {
		for (int i = 0; i<8; i++) {
			for (int y =0; y<8; y++) {
				gameBoard[i][y] = Board.EMPTY;
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
			if ((playerColor == Board.BLACK) && (blackAddPieces <= 0) || (playerColor == Board.WHITE) && (whiteAddPieces <= 0)) {
				return;
			}
			else {
				gameBoard[m.x1][m.y1] = playerColor;
				if (playerColor == Board.WHITE) {
					whiteAddPieces--;
				}
				else if (playerColor == Board.BLACK){
					blackAddPieces--;
				}
			}
		}
		else if (m.moveKind == Move.STEP) {
			gameBoard[m.x2][m.y2] = Board.EMPTY;  //Set old positions to empty
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
			gameBoard[m.x1][m.y1] = Board.EMPTY;
			if (playerColor == Board.WHITE) {
				whiteAddPieces++;
			}
			else if (playerColor == Board.BLACK) {
				blackAddPieces++;
			}
		}
		else if (m.moveKind == Move.STEP) {
			gameBoard[m.x1][m.y1] = Board.EMPTY;
			gameBoard[m.x2][m.y2] = playerColor;
		}
	}

	/**
	 * getSquare takes an int x and int y and returns the piece in that square
	 * If square (x, y) are invalid coordinates, getSquare returns which is always Board.EMPTY
	 * @param x is the x-coordinate of the square
	 * @param y is the y-coordinate of the square
	 * @return int that reflects the square's contents (either Board.WHITE, Board.BLACK, Board.EMPTY)
	 */
	 protected int getSquare(int x, int y) {
		 if (x<0 || x>7 || y<0 || y>7) {
			 return Board.EMPTY;
		 }
		 else {
			 return gameBoard[x][y];
		}
	 }

	protected DList listLegalMoves(int x, int y, int player) {
		return new DList();
	}


    /**
     * isNetwork returns true if "this" board contains a network for the "player"
     * and false if otherwise.
     *
     * @param player the player to check for networks.
     * @return true if "this" board has a network for "player," false if otherwise.
     */
    protected boolean isNetwork(int player) {
	if (!enoughPieces(player) || !inGoal(player)) {
	    return false;
	}else {
	}
	    
    }
    
    /**
     * enoughPieces retruns true if "this" board has enough pieces to make a network.
     *
     * @param player the player to check for qualifying networks.
     * @return true if board qualifies for a network for player, false if otherise.
     */
    private boolean enoughPieces(int player) {
	if (player == WHITE) {
	    if (whiteAddPieces > 4) {
		return false;
	    }
	}else {
	    if (blackAddPieces > 4) {
		return false;
	    }
	}
	return true;
    }


    /** 
     * inGoal determines if there are pieces in both goals for a given player.
     *
     * @param player is the player to check the goals for.
     * @return true if there are goals on both goals, false if otherwise.
     */

    private boolean inGoal(int player) {
	boolean goal1 = false;
	boolean goal2 = false;
	for (int y = 1; y > 6 || goal1; y++) {
	    if (player == WHITE) {
		if (this.getSquare(x, y) == player) {
		    goal1 = true;
		}
	    }else {
		if (this.getSquare(y, x) == player) {
		    goal2 = true;
		}
	    }
	}
	if (goal1) {
	    x = 7;
	    for (int y = 1; y > 6 || goal2; y++) {
		if (player == WHITE) {
		    if (this.getSquare(x, y) == player) {
			goal2 = true;
		    }
		}else {
		    if (this.getSquare(y, x) == player) {
			goal2 = true;
		    }
		}
	    }
	}else {
	    return false;
	}
	return goal1 && goal2;
    }

    /**
     * listConnections returns a DList containing all the pieces with connections
     * to the piece in coordinates (x, y) on "this" board.  The DList contains
     * a list of two element arrays containing another two element array of the form
     * {x, y} in index 0 and a marker integer, default 0, for DFS in index 1.
     *
     * @param x the x-coordinate of the piece.
     * @param y the y-coordinate of the piece.
     * @return a DList with all the pieces that make connections with the piece.
     */

    protected DList listConnections(int x, int y){
	DList connections = new DList();
	int player = this.getSquare(x, y)
	int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}, {1,-1}, {-1, -1}, {1, 1}, {-1, 1}};
	for (i = 0; i < 7; i++) {
	    listConnectionHelper(connections, player, x + directions[i][0], y + directions[i][1], 
				 directions[i]);
	}
	return connections;
    }

    /**
     * listConnectionHelper recursively adds to a list of all connections in the given direction for
     * a given player.  
     *
     * @param connectionList is the existing list of connections.
     * @param player is the player to check for connections to.
     * @param x is the x coord.
     * @param y is the y coord.
     * @param direction is the length 2 array with x and y modifiers corresponding to the direction.
     */
    private void listConnectionHelper(DList connectionList,int player, int x, int y, int[] direction) {
	if (getSquare(x, y) == player) {
	    int[] coord = {{x, y}, {0}};
	    connectionList.insertBack(coord);
	}else if (getSquare(x, y) == EMPTY && x > 0 && x < 7 && y > 0 && y < 7){
	    listConnectionHelper(connectionList, player, x + direction[0], y + direction[1], direction);
	}
    } 
	
}