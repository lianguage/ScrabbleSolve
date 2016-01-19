package WordsBoard;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * WordsSquare is a class designed to hold all the data that a scrabble square could want to hold.
 * Since parts of the words analysis will take place with verticals being independently of horizontals, the respective variables in the words square are held independently.
 * These variables are: unaryrscore, status and onecharwords;
 * 
 * 
 * @author Liang
 *
 */


class WordsSquare {
	Letter letter;
	ScoreBonus type;
	int unaryrowscore = 0;
	int unarycolscore = 0;
	Status statusbyrow = Status.UNKNOWN;
	Status statusbycol = Status.UNKNOWN;
	Set<Letter> onecharrow = new HashSet<Letter>();
	Set<Letter> onecharcol = new HashSet<Letter>();
	
	WordsSquare(){
		letter = Letter._;
	}
	WordsSquare(ScoreBonus type){
		letter = Letter._;
		this.type = type;
	}
	protected void setScoreBonus(ScoreBonus type){
		this.type = type;
	}
	public ScoreBonus getScoreBonus(){
		return type;
	}
	boolean isOccupied(){
		if(letter == Letter._){
			return false;
		}
		else{
			return true;
		}
	}
	protected void setLetter(char letter){
		if (letter <= 'z' && letter >= 'a'){
			this.letter = Letter.CharAsLetter(letter);
		}
		else{
			System.out.println("setLetter must only be used with alphanumeric characters");
			throw new InvalidParameterException();
		}
	}
	
	public void setLetter(Letter letter){
		this.letter = letter;
	}
	public Letter getLetter(){
		return letter;
	}
	
	/**
	 * returns a MetaData token that will return data specific to a square if it was being iterated across row wise\
	 * 
	 * use this when you are iterating across the squares row wise
	 * @return
	 */
	
	MetaData RowData(){
		return new MetaData(){

			public Status getStatus() {
				return statusbyrow;
			}
			public void setStatus(Status status){
				//assert(statusbyrow == Status.UNKNOWN); //Statuses should only be set once per analysis, and then not be changed until the board changes.
				if(statusbyrow != Status.UNKNOWN){throw new IllegalStateException("state was not unknown when it was modified, the value being modified was:" + statusbyrow.toString());}
				statusbyrow = status;
			}
			public Set<Letter> oneCharMoves() {
				return onecharrow;
			}
			
			public int getUnaryScore(){
				return unaryrowscore;
			}
			
			public void setUnaryScore( int singleletterscore){
				unaryrowscore = singleletterscore;
			}
			
			public int getOrthoUnaryScore(){
				return unarycolscore;
			}
			
			public void addOneCharMoves(Collection<Letter> letters){
				onecharrow.addAll(letters);
			}
			public Letter getLetter() {
				return letter;
			}
			public ScoreBonus getScoreBonus() {
				return type;
			}
			public Status getOrthoStatus(){
				return statusbycol;
			}
			public Set<Letter> orthoOneCharMoves(){
				return onecharcol;
			}
		};
	}
	
	/**
	 * returns a metadata token that will access the squares in a chord as if it were being accessed columnwise.
	 * 
	 * This is used when iterating along a column
	 * @return
	 */
	
	MetaData ColData(){
		return new MetaData(){

			public Status getStatus() {
				return statusbycol;
			}
			public void setStatus(Status status){
				assert statusbycol == Status.UNKNOWN;
				statusbycol = status;
			}
			public Set<Letter> oneCharMoves() {
				return onecharcol;
			}
			public void addOneCharMoves(Collection<Letter> letters){
				onecharcol.addAll(letters);
			}
			public Letter getLetter() {
				return letter;
			}
			public ScoreBonus getScoreBonus() {
				return type;
			}
			
			public int getUnaryScore(){
				return unarycolscore;
			}
			
			public void setUnaryScore( int singleletterscore){
				unarycolscore = singleletterscore;
			}
			
			public int getOrthoUnaryScore(){
				return unaryrowscore;
			}
			
			public Status getOrthoStatus(){
				return statusbyrow;
			}
			public Set<Letter> orthoOneCharMoves(){
				return onecharrow;
			}
		};
	}
}