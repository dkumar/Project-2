/* MachinePlayer.java */

package player;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {

  private int playerColor;
  private Board machineBoard;


  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
  public MachinePlayer(int color) {
	this.playerColor = color;
	machineBoard = new Board();
  }

  // Creates a machine player with the given color and search depth.  Color is
  // either 0 (black) or 1 (white).  (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
	  this.playerColor = color;
	  machineBoard = new Board();
  }

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
    return new Move();
  }

  // If the Move m is legal, records the move as a move by the opponent
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method allows your opponents to inform you of their moves.
  public boolean opponentMove(Move m) {
	  if (!isLegal(m, (this.playerColor+1)%2)) {  //call isLegal with Move and other player's color
		  return false;
	  }
	  else {
		  machineBoard.makeMove(m, (int) (this.playerColor+1)%2);
		  return true;
	  }
  }

  // If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
  public boolean forceMove(Move m) {
	if (!isLegal(m, this.playerColor)) {
		return false;
	}
	else {
		machineBoard.makeMove(m, this.playerColor);
		return true;
	}
  }

    /**
     * isLegal will return true if the given Move m is a legal move on the
     * the current game board based on the rules of Network and false if otherwise
     *
     * @param m the Move object that will be checked
     * @param playerColor is the color of the player making the Move
     * @return true if the move is legal false otherwise.
     */
  protected boolean isLegal(Move m, int playerColor) {
	  //cluster rule not implemented yet
	  //run through 2 for loops or check corners
	  //isLegal private?
	  if (machineBoard.gameBoard[m.x1][m.x2] != Board.EMPTY) {  			//cell cannot be occupied
		  return false;
	  }
	  for (int i = 0; i <= 7; i++) {
		  if (((playerColor == Board.BLACK) && ((m.x1 == 0 && m.y1 == i) || (m.x1 == 7 && m.y1 == i)))  ||	//BLACK cannot place pieces in: 01-06, 71-76
		  ((playerColor == Board.WHITE) && ((m.x1 == i && m.y1 == 0) || (m.x1 == i && m.y1 == 7)))) {		//WHITE cannot place pieces in: 10-60, 17-67
		  return false;																					//Neither can have pieces in 00, 07, 70, 77 (which code checks for)
		  }
	  }
	  return true;
	  }

    /**
     * checkNeighbor will return the number of pieces of color playerColor that neighbor a square on the game board with coordinates (x,y).
     * A piece neighbors a square if it is connected to the square orthogonally or diagonally.
     * It ignores any pieces that are not of color playerColor
     * @param x is the x-coordinate of the game board square
     * @param y is the y-coorindate of the game board square
     * @param playerColor is the color of pieces to search for
     * @returns the number of neighbors of a square on the game board with coordinates (x,y)
     */
  private int checkNeighbor(int x, int y, int playerColor) {
	  return 0;
  }
}