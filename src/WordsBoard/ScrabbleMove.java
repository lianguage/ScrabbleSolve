package WordsBoard;


public class ScrabbleMove implements Comparable<ScrabbleMove>{
	
	private boolean isVertical;
	private int primaryIdentifier;
	private int inChordIdentifier;
	private int Score;
	private String word;
	
	public boolean isVertical(){return isVertical;}
	public int primaryIdentifier(){ return primaryIdentifier;}
	public int inChordIdentifier(){ return inChordIdentifier;}
	public int Score(){ return Score; }
	public String Word(){ return word;}
	
	public ScrabbleMove(boolean isVertical, int primaryIdentifier,int inChordIdentifier, int Score, String word){
		this.isVertical = isVertical;
		this.primaryIdentifier = primaryIdentifier;
		this.inChordIdentifier = inChordIdentifier;
		this.Score = Score;
		this.word = word;
	}
	
	@Override
	public int compareTo(ScrabbleMove othermove) {
		if(othermove.Score == this.Score){
			return 0;
		}
		else if(othermove.Score > this.Score){
			return -1;
		}
		else if(othermove.Score < this.Score){
			return 1;
		}
		else{
			return 0;
		}
	}
	
	public void printMove(){
		System.out.print("score:" + Score + " | Word:" + word + " | co-ords:");
		if(isVertical){
			System.out.println("column no. " + primaryIdentifier + " - @" + inChordIdentifier );
		}
		else{
			System.out.println("row no. " + primaryIdentifier + " - @" + inChordIdentifier );
		}
	}
}
