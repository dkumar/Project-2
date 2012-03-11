/* MachinePlayer.java */

package player;
import list.*;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {
  public final static int COMPUTER_WIN = 1;
  public final static int HUMAN_WIN = -1;
  public final static int VARIABLE_SEARCH = -2;
  private final int searchDepth;
  private int playerColor;
  private int opponentColor;
  private Board machineBoard;


  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
  public MachinePlayer(int color) {
      if(color == Board.WHITE){
          opponentColor = Board.BLACK;
      }else{
          opponentColor = Board.WHITE;
      }
      this.playerColor = color;
      machineBoard = new Board();
      searchDepth = VARIABLE_SEARCH;
  }

  // Creates a machine player with the given color and search depth.  Color is
  // either 0 (black) or 1 (white).  (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
	  if(color == Board.WHITE){
		  opponentColor = Board.BLACK;
	  }else{
		  opponentColor = Board.WHITE;
	  }
	  this.playerColor = color;
	  machineBoard = new Board();
      this.searchDepth = searchDepth;
  }

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
      Move move;
      if(searchDepth == VARIABLE_SEARCH){
          int variableDepth;
	  if((playerColor == Board.WHITE && (machineBoard.whiteAddPieces > 0)) || (playerColor == Board.BLACK && (machineBoard.blackAddPieces > 0))){
	      variableDepth = 3;
          }else{
	      variableDepth = 2; //We can decide later what values we should use for variableDepth depending on how fast it runs when we test.
	  }
	  move = (minimax(playerColor,variableDepth,-2,2)).getMove();
      }else{
          move = (minimax(playerColor,searchDepth,-2,2)).getMove();
      }
      machineBoard.makeMove(move, this.playerColor);
      return move;
  }

  /**
 * minimax() assigns a score to a board on the game tree, using alpha-beta pruning for efficiency,
 * in order to choose the next best possible move.
 * @param side is the color of the player who's moves we are currently looking for.
 * @param depth is how many more turns the algorithm must look ahead.
 * @param alpha is the score the computer knows with certainty it can achieve.
 * @param beta is the score the opponent knows it can achieve.
 * @return a ScoredMove which holds the highest scoring move.
 */
  private ScoredMove minimax(int side, int depth, float alpha, float beta) {
      ScoredMove myBest = new ScoredMove();
      ScoredMove reply;
      int otherPlayer;

      if(side == Board.BLACK){
          otherPlayer = Board.WHITE;
      }else{
	      otherPlayer = Board.BLACK;
      }

      boolean whiteNetwork = machineBoard.isNetwork(Board.WHITE);
      boolean blackNetwork = machineBoard.isNetwork(Board.BLACK);
      if(depth <= 0 || whiteNetwork || blackNetwork){
          ScoredMove newBest = new ScoredMove();
          if(whiteNetwork){
	          if(Board.WHITE == playerColor){
	              newBest.setScore(COMPUTER_WIN);
	          }else{
	              newBest.setScore(HUMAN_WIN);
	          }
          }else if(blackNetwork){
	          if(Board.BLACK == playerColor){
	              newBest.setScore(COMPUTER_WIN);
	          }else{
		          newBest.setScore(HUMAN_WIN);
	          }
          }else{
	          newBest.setScore(evaluateBoard(machineBoard));
          }
       	  return newBest;
      }

      if(side == playerColor){
          myBest.setScore(alpha);
      }else{
          myBest.setScore(beta);
      }

      DList validMoves = legalMoves(side);
      try{
          DListNode curr = validMoves.front();
	      while(curr.isValidNode()){
	          Move move = (Move)(curr.item());
	          machineBoard.makeMove(move,side);
	          curr = curr.next();
	          reply = minimax(otherPlayer, depth-1, alpha, beta);
	          machineBoard.undoMove(move,side);
	          if((side == playerColor) && (reply.getScore() > myBest.getScore())){
	              myBest.setMove(move);
		          myBest.setScore(reply.getScore());
		          alpha = reply.getScore();
	          }else if ((side == opponentColor) && (reply.getScore() < myBest.getScore())){
		          myBest.setMove(move);
		          myBest.setScore(reply.getScore());
		          beta = reply.getScore();
	          }
	          if (alpha >= beta) {
	              return myBest;
	          }
	      }
      }catch(InvalidNodeException e){
          System.out.println(e);
      }
      return myBest;
  }

/**
   * Constructs a doubly-linked list of all legal moves that may be done on the current board
   * by a specified player.
   * @param side is the color of the player who's moves we are trying to determine.
   * @return a doubly-linked list of a player's legal moves.
   */
  private DList legalMoves(int side){
      DList moves = new DList();
      if((side == Board.WHITE && (machineBoard.whiteAddPieces > 0)) || (side == Board.BLACK && (machineBoard.blackAddPieces> 0))){
          for(int j = 0; j <= 7; j++){
	          for(int i = 0; i <= 7; i++){
	              if(machineBoard.getSquare(i,j) == Board.EMPTY){
		          Move newMove = new Move(i,j);
		          if(isLegal(newMove,side)){
		              moves.insertBack(newMove);
		          }
	       	  }
	      }
	  }
      }else{
          int[][] pieceCoordinates = new int[10][];
	      int index = 0; //Keeps track of what index we are in our pieceCoordinates array.
	      for(int j = 0; j <= 7; j++){
	          for(int i = 0; i <= 7; i++){
	              if(machineBoard.getSquare(i,j) == side){
		              int[] coordinateArray = {i,j};
		              pieceCoordinates[index] = coordinateArray;
		              index++;
		          }
	          }
          }
	      for(int k = 0; k <= 7; k++){
	          for(int m = 0; m <= 7; m++){
	              if(machineBoard.getSquare(m,k) == Board.EMPTY){
		              for(int n = 0; n < pieceCoordinates.length; n++){
		                  Move newMove = new Move(m,k,pieceCoordinates[n][0],pieceCoordinates[n][1]);
			          if(isLegal(newMove,side)){
			              moves.insertBack(newMove);
			          }
		              }
	              }
	          }
	      }
      }
      return moves;
  }

  // If the Move m is legal, records the move as a move by the opponent
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method allows your opponents to inform you of their moves.
  public boolean opponentMove(Move m) {
	  if (!isLegal(m, this.opponentColor)) {  //call isLegal with Move and other player's color
		  return false;
	  }
	  else {
		  machineBoard.makeMove(m, this.opponentColor);
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
  private boolean isLegal(Move m, int playerColor) {
	  if (m.x1<0 || m.x1>7 || m.y1<0 || m.y1>7 || m.x2<0 || m.x2>7 || m.y2<0 || m.y2>7) {							//illegal coordinates
		  return false;
	  }
	  if (machineBoard.getSquare(m.x1, m.y1) != Board.EMPTY) {  													//cell cannot be occupied
		  return false;
	  }
	  if ((m.moveKind == Move.ADD) && ((playerColor == Board.WHITE && machineBoard.whiteAddPieces<=0) || (playerColor == Board.BLACK && machineBoard.blackAddPieces<=0))) {
		  return false;
	  }
	  if ((m.moveKind == Move.STEP) && ((playerColor == Board.WHITE && machineBoard.whiteAddPieces>0) || (playerColor == Board.BLACK && machineBoard.blackAddPieces>0))) {
		  return false;
	  }
	  for (int i = 0; i <= 7; i++) {
		  if (((playerColor == Board.BLACK) && ((m.x1 == 0 && m.y1 == i) || (m.x1 == 7 && m.y1 == i)))  ||	//Board.BLACK cannot place pieces in: 01-06, 71-76
		  ((playerColor == Board.WHITE) && ((m.x1 == i && m.y1 == 0) || (m.x1 == i && m.y1 == 7)))) {		//Board.WHITE cannot place pieces in: 10-60, 17-67
		      return false;																					//Neither can have pieces in 00, 07, 70, 77 (which code checks for)
		  }
	  }

	  if(clusterCheck(m.x1, m.y1, playerColor)) {
		  return false;
	  }
	  else {
		  return true;
	  }

  }

   /**
     * checkNeighbor checks to see if less than 2 pieces neighbor square (x,y)
     * A piece neighbors a square if it is connected to the square orthogonally and is the same color as playerColor
     * @param x is the x-coordinate of the game board square
     * @param y is the y-coorindate of the game board square
     * @param playerColor is the color of pieces to search for
     * @return true if less than 2 pieces neighbor square (x,y); false otherwise
     */
   private boolean checkNeighbor(int x, int y, int playerColor) {
	   int neighbors = 0;
	   if (machineBoard.getSquare(x-1, y-1) == playerColor) {
		   neighbors++;
	   }
	   if (machineBoard.getSquare(x-1, y) == playerColor) {
		   neighbors++;
	   }
	   if (machineBoard.getSquare(x-1, y+1) == playerColor) {
		   neighbors++;
	   }
	   if (machineBoard.getSquare(x, y+1) == playerColor) {
		   neighbors++;
	   }
	   if (machineBoard.getSquare(x+1, y+1) == playerColor) {
		   neighbors++;
	   }
	   if (machineBoard.getSquare(x+1, y) == playerColor) {
		   neighbors++;
	   }
	   if (machineBoard.getSquare(x+1, y-1) == playerColor) {
		   neighbors++;
	   }
	   if (machineBoard.getSquare(x, y-1) == playerColor) {
		   neighbors++;
	   }
	   if (neighbors >= 2) {
		   return false;
	   }
	   else {
		   return true;
	   }
   }

   /**
     * clusterCheck checks if there will be a cluster if a piece of color playerColor is in square (x,y)
     * A cluster is formed when one piece is adjacent to two others of the same playerColor
     * @param x is the x-coordinate of the game board square
	 * @param y is the y-coorindate of the game board square
     * @param playerColor is the color of pieces to search for
     * @return true if there will be a cluster if a piece of color playerColor is placed in square (x,y); false otherwise
     */
   private boolean clusterCheck(int x, int y, int playerColor) {
	   boolean upLeft = true, left = true, downLeft = true, down = true, downRight = true, right = true, upRight = true, up = true, middle;
	   if (machineBoard.getSquare(x-1, y-1) == playerColor) {
		   upLeft = checkNeighbor(x-1, y-1, playerColor);
	   }
	   if (machineBoard.getSquare(x-1, y) == playerColor) {
		   left = checkNeighbor(x-1, y, playerColor);
	   }
	   if (machineBoard.getSquare(x-1, y+1) == playerColor) {
		   downLeft = checkNeighbor(x-1, y-1, playerColor);
	   }
	   if (machineBoard.getSquare(x, y+1) == playerColor) {
		   down = checkNeighbor(x, y+1, playerColor);
	   }
	   if (machineBoard.getSquare(x+1, y+1) == playerColor) {
		   downRight = checkNeighbor(x+1, y+1, playerColor);
	   }
	   if (machineBoard.getSquare(x+1, y) == playerColor) {
		   right = checkNeighbor(x+1, y, playerColor);
	   }
	   if (machineBoard.getSquare(x+1, y-1) == playerColor) {
		   upRight = checkNeighbor(x+1, y-1, playerColor);
	   }
	   if (machineBoard.getSquare(x, y-1) == playerColor) {
		   up = checkNeighbor(x, y-1, playerColor);
	   }
	   middle = checkNeighbor(x, y, playerColor);
	   return !(upLeft && left && downLeft && down && downRight && right && upRight && up && middle);
   }


  protected float evaluateBoard(Board b) {
	  return 0;
  }
}