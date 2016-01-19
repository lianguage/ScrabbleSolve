package WordsBoard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Board segment checker is designed to check a particular segment on a words board for valid words.
 * It has a start and end, and all c
 * @author Liang
 *
 */

class BoardSegmentChecker{
		
	private List<SegmentNibble> nibbles = new ArrayList<SegmentNibble>();
	private List<LocalFoundWord> foundwords = new ArrayList<LocalFoundWord>();
	private String suffix;
	private boolean isvalid = false;
	private int inherentscore;
	Collection<Letter> availableletters;
	String segmentregex;
	int numberofblanks;
	
	BoardSegmentChecker(List<SegmentNibble> newnibble, String suffix, Collection<Letter> availableletters, int numberofblanks){
		nibbles = newnibble;
		this.numberofblanks = numberofblanks;
		this.availableletters = availableletters;
		this.suffix = suffix;
		this.isvalid = checkIfValid();
		if(isvalid){
			this.segmentregex = segmentRegex();
			findWords();
		}
		for(SegmentNibble nibble: newnibble){
			inherentscore += nibble.inherentscore;
		}
		this.inherentscore += Letter.pointsPerWord(suffix);
	}
	
	protected boolean isValid(){
		return isvalid;
	}
	
	public List<LocalFoundWord> getFoundWords(){
		return foundwords;
	}
	
	public List<SegmentNibble> getNibbles(){
		return nibbles;
	}
	
	private void findWords(){
		Collection<String> wordsubset = ScrabbleDictionary.matchesRegex(segmentregex);
		
		for(String word: wordsubset){
			//System.out.println(word);
			if(isValidLetterUsage(word)){
				//System.out.println("word found!:" + word);
				foundwords.add( new LocalFoundWord(word, calculateScore()));
			}
		}
	}
	
	
	//isValidLetterUsage should be used along with calculate score and add word, since each runthrouhg of it will
	//store data in the variables local to the SegmentChecker.
	private boolean isValidLetterUsage(String word){
		Collection<Letter> remainingletters = new ArrayList<Letter>(availableletters);
		int letterindex = 0;
		int tempblanks = numberofblanks;
		for(SegmentNibble nibble: nibbles){
			letterindex += nibble.prefix.length();
			Letter currentletter = Letter.CharAsLetter(word.charAt(letterindex));
			boolean blankused = false;
			if(!remainingletters.remove(currentletter)){
				if(tempblanks > 0){
					blankused = true;
					tempblanks--;
				}
				else{
					return false;
				}
			}
			if(blankused){
				nibble.templetter = Letter.x;
			}
			else{
				nibble.templetter = currentletter;
				letterindex ++;
			}
		}
		return true;
	}
	
	private String segmentRegex(){
		String segmentregex = "^";
		for(SegmentNibble nibble: nibbles){
			segmentregex = segmentregex.concat(nibble.prefix);
			if(nibble.square.getOrthoStatus() == Status.BLANK){
				if(numberofblanks >= 0){
					segmentregex = segmentregex.concat(".");
				}
				else{
					segmentregex = segmentregex.concat("]");
					for(Letter letter: availableletters){
						segmentregex = segmentregex.concat(letter.toString());
						segmentregex = segmentregex.concat("]");
					}
					segmentregex = segmentregex.concat("]");
				}
			}
			else{
				segmentregex = segmentregex.concat("[");
				for(Letter letter: nibble.square.orthoOneCharMoves()){
					segmentregex = segmentregex.concat(letter.toString());
				}
				segmentregex = segmentregex.concat("]");
			}
		}
		segmentregex = segmentregex.concat(suffix + "$");
		return segmentregex;
	}
	
	private int calculateScore(){
		int score =0;
		int orthogonalscore = 0;
		int multiplier = 1;
		for(SegmentNibble nibble: nibbles){
			int orthomultiplier = 1;
			if(nibble.square.getScoreBonus() == ScoreBonus.DL){
				score += Letter.pointsPerLetter(nibble.templetter)*2;
			}
			else if(nibble.square.getScoreBonus() == ScoreBonus.TL){
				score += Letter.pointsPerLetter(nibble.templetter)*3;
			}
			else if(nibble.square.getScoreBonus() == ScoreBonus.DW){
				multiplier *= 2;
				orthomultiplier = 2;
				score += Letter.pointsPerLetter(nibble.templetter);
			}
			else if(nibble.square.getScoreBonus() == ScoreBonus.TW){
				multiplier *= 3;
				orthomultiplier = 3;
				score += Letter.pointsPerLetter(nibble.templetter);
			}
			else{
				score += Letter.pointsPerLetter(nibble.templetter);
			}
			orthogonalscore += nibble.square.getUnaryScore()*orthomultiplier;
		}
		score += inherentscore;
		return ((score*multiplier)+orthogonalscore);
	}


	private boolean checkIfValid(){
		boolean noimpossibletiles = true;
		boolean hasadjacent = false;
		for(SegmentNibble nibz: nibbles){
			
			if((nibz.square.orthoOneCharMoves().size() <= 0) && (nibz.square.getOrthoStatus() != Status.BLANK)){
				noimpossibletiles = false;
			}
		}
		for(SegmentNibble nibz:nibbles){
			if(nibz.square.getStatus() != Status.BLANK	||	nibz.square.getOrthoStatus() != Status.BLANK ){
					hasadjacent = true;
					return hasadjacent && noimpossibletiles;
			}
		}
		return false;
	}

}
