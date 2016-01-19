package WordsBoard;

import java.util.Collection;
import java.util.Set;


public interface MetaData{
	public Letter getLetter();
	public Status getStatus();
	public Status getOrthoStatus();
	public void setStatus(Status status);
	public void addOneCharMoves(Collection<Letter> moves);
	public Set<Letter> oneCharMoves();
	public Set<Letter> orthoOneCharMoves();
	public ScoreBonus getScoreBonus();
	public int getUnaryScore();
	public void setUnaryScore( int singleletterscore);
	public int getOrthoUnaryScore();
}
