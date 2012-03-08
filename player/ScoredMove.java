package player;
/**
 * A public class that holds a move and it's corresponding score for the  
 * current board.
 *
 */
public class ScoredMove {
  private float score;
  private Move move;
  
  /**
   * Sets the score corresponding to the move for this ScoredMove.
   * @param newScore is the new floating point score of this ScoredMove.
   */
  public void setScore(float newScore){
	  score = newScore;
  }
  /**
   * Returns the score of this ScoredMove.
   * @return a floating point score for ScoredMove's move.
   */
  public float getScore(){
	  return score;
  }
  /**
   * Sets the move corresponding to a score for this ScoredMove.
   * @param newMove is the new move for this current board who we have given a score.
   */
  public void setMove(Move newMove){
	  move = newMove;
  }
  
  /**
   * Returns the move of this ScoredMove.
   * @return a move for this current board.
   */
  public Move getMove(){
	  return move;
  }
}
