package WordsBoard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

class WordsOnAChord{
	
	/**
	 * This Class represents an entire chord (a row or column, some people may call it a slice, or a rank or a co-ord, I decided to call it a chord.) on the board.
	 * 
	 * It is initialised via an iterable, and pointers to the squares on the chord are added to a list 'hotdog' in the form of another class called SegmentNibble.
	 * This means that there is no direct way of relating the data of words on a chord to the actual board, and all operations are done through the MetaData interface.
	 * 
	 * The segment nibble is explained more in the segment nibble class 
	 * 
	 * 	
	 */
	

	List<SegmentNibble> hotdog = new ArrayList<SegmentNibble>();  //contains all the nibbles along a single chord of the board
	List<BoardSegmentChecker> bites = new ArrayList<BoardSegmentChecker>(); //contains segments created from intervals in the chord. See BoardSegmentChecker for more details.
	Collection<Letter> availableletters; //stores the link to the letters available for use in forming words within the subintervals(segments) of the chord
	int numberofblanks; //stores the number of blank tiles that are available to the player, following the format of the upper level words board class
	String suffix;
	int chordnumber;
	
	WordsOnAChord(Iterable<MetaData> roworcoliterator, Collection<Letter> availableletters,int numberofblanks){
		this.availableletters = availableletters;
		this.numberofblanks = numberofblanks;
		Iterator<MetaData> iterator = roworcoliterator.iterator();
		String nibbletemp = "";
		int index = 0;
		while(iterator.hasNext()){
			MetaData current = iterator.next();
			if(current.getStatus() == Status.LETTR){
				nibbletemp = nibbletemp.concat(current.getLetter().toString());
			}
			else{
				SegmentNibble newnibble = new SegmentNibble(nibbletemp, index, current);
				hotdog.add(newnibble);
				nibbletemp = "";
			}
			index++;
		}
		this.suffix = nibbletemp;
		addAllPossibleSegments();
	}
	
	private String getSuffix(int index){
		if(index >= (hotdog.size()-1)){
			return suffix;
		}
		else{
			return hotdog.get(index+1).prefix;
		}
	}
	
	private BoardSegmentChecker makeNewBite(int start,int end){
		assert(start < hotdog.size() );
		assert( end < hotdog.size());
		List<SegmentNibble> newsegment = new ArrayList<SegmentNibble>();
		for(int i = start ; i <= end ; i++){
			newsegment.add(hotdog.get(i));
		}
		BoardSegmentChecker madebite = new BoardSegmentChecker(newsegment,getSuffix(end),availableletters, numberofblanks);
		return madebite;
	}
	
	
	
	private void addAllPossibleSegments(){
		for(int quantity = 1; (quantity <= hotdog.size())&&(quantity <= availableletters.size()); quantity++){
			for(int start = 0; start <= (hotdog.size()-quantity) ; start++ ){
				int end = start + quantity - 1;
				BoardSegmentChecker newbite = makeNewBite(start,end);
				if(newbite.isValid()){
					bites.add(newbite);
				}
			}
		}
	}
}