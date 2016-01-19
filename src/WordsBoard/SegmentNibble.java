package WordsBoard;

import java.util.Collection;



class SegmentNibble{
	/**
	 * Segment nibble is a class that represents the influences around a single tile by adjacent tiles. Eg tiles running adjacent, and the word that can be formed along the column.
	 */
	
	
	protected String prefix;
	protected int index;
	protected int inherentscore;
	protected MetaData square;
	protected int restrictivepriority = 100; //the degrees of freedom in terms of letters that can occupy this nibble. The more degrees of freedom the more search depth. 
											//100 indicates default(needs to only be 26 - since 26 letters in alphabet, but 100 cos it looks nice & is obvious that it is in it's default state)
	public Letter templetter = Letter._;
	protected Collection<String> dictionarysubset;
	
	SegmentNibble( String prefix, int startindex, MetaData asdf){
		this.prefix = prefix;
		this.index = startindex;
		this.square = asdf;
		setRestrictivePriority();
		this.inherentscore = Letter.pointsPerWord(prefix);
		this.inherentscore += square.getOrthoUnaryScore();
	}
	
	private void setRestrictivePriority(){
		if(square.getOrthoStatus() != Status.BLANK){
			restrictivepriority = square.orthoOneCharMoves().size();
		}
	}
	
	
}