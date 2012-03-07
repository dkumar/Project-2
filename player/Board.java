package player;

public class Board {
	public final static int EMPTY = -1;
	public final static int BLACK = 0;
	public final static int WHITE = 1;

	protected int[][] gameBoard = new int[8][8];
	protected int whiteAddPieces = 10;   //Machine player's pieces
	protected int blackAddPieces = 10;

	/*
		Sets each item on the board to EMPTY.
	*/
	public Board() {
		for (int i = 0; i<8; i++) {
			for (int y =0; y<8; y++) {
				gameBoard[i][y] = EMPTY;
			}
		}
	}

	/*
		makeMove takes a Move m and changes "this" board accordingly.
		Assumes Move m is a legal move.
		If Move m is an ADD Move, makeMove decrements the number of pieces of playerColor accordingly.
		@Move m is the Move object to be applied to "this board"
		@int playerColor is the color of the player making the move
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

	/*
		undoMove takes a Move m and undoes the Move on the game board
	*/
	protected void undoMove(Move m, int playerColor) {
	}

//	protected DList listLegalMoves(int x, int y, int player) {
//	}

}