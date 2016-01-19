package WordsBoard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import java.util.Iterator;
import java.util.List;


public class WordsBoard  {

	private WordsSquare[][] board;
	private final MetaData nullsquare = new WordsSquare().ColData();
	public final int height = 15;
	public final int width = 15;
	public final int maxindex = 14;
	Collection<Letter> availableletters;
	Collection<Letter> nonblankletters;
	int numberofblanks = 0;
	
	/**
	 * The rowForwards (as with the other methods that generate iterators) is designed to create an iterable that will iteratively
	 * access a particular chord of the words board as specified by the integer in the method's parameters.
	 * @param chordno
	 * @return
	 */
	
	public Iterable<MetaData> rowForwards(int chordno) {
		SquareIterator iterator = new SquareIterator();
		iterator.goForwards();
		iterator.iterateByRow();
		iterator.setChordNo(chordno);
		return new iteratorReflector(iterator);
	}
	public Iterable<MetaData> rowBackwards(int chordno){
		SquareIterator iterator = new SquareIterator();
		iterator.goBackwards();
		iterator.iterateByRow();
		iterator.setChordNo(chordno);
		return new iteratorReflector(iterator);
	}
	public Iterable<MetaData> colForwards(int chordno){
		SquareIterator iterator = new SquareIterator();
		iterator.goForwards();
		iterator.iterateByCol();
		iterator.setChordNo(chordno);
		return new iteratorReflector(iterator);
	}
	public Iterable<MetaData> colBackwards(int chordno){
		SquareIterator iterator = new SquareIterator();
		iterator.goBackwards();
		iterator.iterateByCol();
		iterator.setChordNo(chordno);
		return new iteratorReflector(iterator);
	}
	
	/**
	 * just a method that turns an iterator into an iterable
	 * @author liang
	 *
	 */
	private class iteratorReflector implements Iterable<MetaData>{
		Iterator<MetaData> iterator;
		iteratorReflector(Iterator<MetaData> iterator){
			this.iterator = iterator;
		}
		public Iterator<MetaData> iterator() {
			return iterator;
		}
	}
	
	/**
	 * Square iterator is designed to iteratively access the elements of the Words Chord it is associated with
	 * when it is first created from an iterable in the respective rowForwards/rowBackwards/colForwards/colBackwards methods.
	 * 
	 * The way it behaves depends on how it is initialised with those respective methods.
	 * 
	 * It also contains the concrete implementations of the MetaData interface. It will return a different implementation of the MetaData interface
	 * depending on how it was initialised.
	 * 
	 * An iterator returned from a rowForwards method call will iterate across a row with the respective number down from the top. The MetaData interface
	 * will be associated with a concrete class that returns data from the row section of the data within a words board square.
	 * @author liang
	 *
	 */
	private class SquareIterator implements Iterator<MetaData>{
		
		int chordnumber;
		int index;
		boolean gobackwards;
		boolean iteratebyrow;
		boolean orthogonal = false;
		
		void iterateByRow(){
			iteratebyrow = true;
		}
		void iterateByCol(){
			iteratebyrow = false;
		}
		void goBackwards(){
			gobackwards = true;
			index = maxindex+1;
		}
		void goForwards(){
			gobackwards = false;
			index = -1;
		}
		void setChordNo(int chordno){
			assert(chordno <= maxindex);
			this.chordnumber = chordno;
		}
		private int row(){
			if(iteratebyrow){
				return chordnumber;
			}
			else{
				return index;
			}
		}
		private int col(){
			if(iteratebyrow){
				return index;
			}
			else{
				return chordnumber;
			}
		}
		public boolean hasNext() {
			if(gobackwards){
				return index>0;
			}
			else{
				return index < maxindex;
			}
		}
		public MetaData next() {
			if(gobackwards){
				index--;
			}
			else{
				index++;
			}
			if(iteratebyrow){
				if(orthogonal){
					return board[row()][col()].ColData();
				}
				else{
					return board[row()][col()].RowData();
				}
			}
			else{
				if(orthogonal){
					return board[row()][col()].RowData();
				}
				else{
					return board[row()][col()].ColData();
				}
			}
		}
		public void remove() {
			System.out.println("you bastard don't use Iterator.remove!");
		}
		
	}
	
	//=========================Marking all the distinctive points=========================TODO
	
	/**
	 * the following methods are designed to set the 'Status' of each square on the board based on it's surrounding letter placements.
	 * This is done partly for code clarity, but also to reduce the need to check adjacent squares for information later on, and thus also reduces algorithm complexity
	 * 
	 */
	
	private void markWordEdges(Iterable<MetaData> iterable, Status status){
		
		boolean prev1blank = true;
		boolean prev2blank = true;
		MetaData previous = nullsquare;
		
		for(MetaData square: iterable){
			if(square.getLetter() == Letter._){
				if(!prev1blank){
					previous = square;
				}
				else if(prev1blank && !prev2blank){
					previous.setStatus(status);
				}
				prev2blank = prev1blank;
				prev1blank = true;
			}
			else{
				
				prev2blank=prev1blank;
				prev1blank=false;
			}
		}
		if(prev1blank && !prev2blank){
			previous.setStatus(status);
		}
	}
	
	private void markWordJoiners(Iterable<MetaData> iterable){
		boolean prev1blank = true;
		boolean prev2blank = true;
		MetaData previous = nullsquare;
		
		for(MetaData square: iterable){
			if(square.getLetter() == Letter._){
				if(!prev1blank){
					previous = square;
				}
				prev2blank = prev1blank;
				prev1blank = true;
			}
			else{
				if(!prev2blank && prev1blank){
					previous.setStatus(Status.MIDDL);
				}
				prev2blank = prev1blank;
				prev1blank = false;
			}
		}
	}
	
	private void markBlanks(Iterable<MetaData> iterable){
		boolean prev1blank = true;
		boolean prev2blank = true;
		MetaData previous = nullsquare;
		
		for(MetaData square: iterable){
			if(square.getLetter() == Letter._){
				if(prev1blank&&prev2blank){
					previous.setStatus(Status.BLANK);
				}
				if(prev1blank){
					previous = square;
				}
				prev2blank = prev1blank;
				prev1blank = true;
			}
			else{
				prev2blank = prev1blank;
				prev1blank = false;
			}
		}
		if(prev1blank&&prev2blank){
			previous.setStatus( Status.BLANK);
		}
	}
	
	private void markLetters(Iterable<MetaData> iterable){
		for(MetaData square: iterable){
			if(square.getLetter() != Letter._){
				square.setStatus(Status.LETTR);
			}
		}
	}
	
	private void markRowEndPoints(int chordno){
		markWordEdges(rowForwards(chordno), Status.FINSH);
	}
	private void markRowStartPoints(int chordno){
		markWordEdges(rowBackwards(chordno), Status.START);
	}
	private void markRowMidPoints(int chordno){
		markWordJoiners(rowForwards(chordno));
	}
	private void markRowBlanks(int chordno){
		markBlanks(rowForwards(chordno));
	}
	private void markRowLetters(int chordno){
		markLetters(rowForwards(chordno));
	}
	private void markFullRow(int chordno){
		markRowLetters(chordno);
		markRowBlanks(chordno);
		markRowMidPoints(chordno);
		markRowStartPoints(chordno);
		markRowEndPoints(chordno);
	}
	
	private void markColEndPoints(int chordno){
		markWordEdges(colForwards(chordno), Status.FINSH);
	}
	private void markColStartPoints(int chordno){
		markWordEdges(colBackwards(chordno), Status.START);
	}
	private void markColMidPoints(int chordno){
		markWordJoiners(colForwards(chordno));
	}
	private void markColBlanks(int chordno){
		markBlanks(colForwards(chordno));
	}
	private void markColLetters(int chordno){
		markLetters(colForwards(chordno));
	}
	private void markFullCol(int chordno){
		markColLetters(chordno);
		markColEndPoints(chordno);
		markColStartPoints(chordno);
		markColMidPoints(chordno);
		markColBlanks(chordno);
		markColLetters(chordno);
	}
	
	private static void printTest(Object stringg){
		String string = stringg.toString();
		System.out.println("============="+string+"=============");
	}
	
	private static void print(Object stringg){
		String string = stringg.toString();
		System.out.print(string);
	}

	private static void println(String stringg){
		String string = stringg.toString();
		System.out.println(string);
	}
	
	//============================Stage 2============================TODO
	//============================Stage 2============================
	//============================Stage 2============================
	//============================Stage 2============================
	//============================Stage 2============================
	//============================Stage 2============================

	
	private class Aterator implements Iterator<MetaData>{
		Iterator<MetaData> originaliterator;
		MetaData current = nullsquare;//TODO this doesn't appear to screw anything up, but its still something to be careful of
		
		Aterator( Iterator<MetaData> iterator ){
			this.originaliterator = iterator;
		}
		public boolean hasNext(){
			return originaliterator.hasNext();
		}
		public MetaData next(){
			this.current = originaliterator.next();
			return current;
		}
		MetaData current(){
			return current;
		}
		public void remove() {
			println("Please don't use the remove method");
		}
	}
	
	private void goToStart(Iterator<MetaData> iterator){
		MetaData currentsquare;
		while(iterator.hasNext()){
			currentsquare = iterator.next();
			if(currentsquare.getStatus() == Status.START){
				break;
			}
		}
	}
	
	private void goToFinish(Iterator<MetaData> iterator){
		MetaData currentsquare;
		while(iterator.hasNext()){
			currentsquare = iterator.next();
			if(currentsquare.getStatus() == Status.FINSH){
				break;
			}
		}
	}

	private String findRestOfWord(Iterator<MetaData> iterator){
		String word = "";
		MetaData currentsquare = null;
		while(iterator.hasNext()){
			currentsquare = iterator.next();
			if(currentsquare.getStatus() != Status.LETTR){
				break;
			}
			word = word.concat(Character.toString(Letter.LetterAsChar(currentsquare.getLetter())));
		}
		while(iterator.hasNext()){
			if(currentsquare.getStatus() != Status.FINSH){
				currentsquare = iterator.next();
				continue;
			}
			break;
		}
		return word;
	}
	

	
	private void calculateStartSingleMoves(Iterable<MetaData> lineOdata){
		Aterator aterator = new Aterator(lineOdata.iterator());
		while(aterator.hasNext()){
			goToStart(aterator);
			MetaData start = aterator.current();
			String word = findRestOfWord(aterator);
			singleLettersStart(word,start);
		}
	}
	
	private void calculateEndingSingleMoves(Iterable<MetaData> reverseLine){
		Aterator aterator = new Aterator(reverseLine.iterator());
		while(aterator.hasNext()){
			goToFinish(aterator);
			MetaData end = aterator.current();
			String word = findRestOfWord(aterator);
			word = new StringBuffer(word).reverse().toString();
			singleLettersFinish(word,end);
		}
	}
	
	private void calculateMiddleSingleMoves(Iterable<MetaData> forwardsLine){
		Aterator aterator = new Aterator(forwardsLine.iterator());
		String word1 = "";
		String word2 = "";
		MetaData middle = null;
		boolean middlefound = false;
		while(aterator.hasNext()){
			if(middlefound){
				while(aterator.hasNext()){
					aterator.next();
					if(aterator.current().getStatus() == Status.FINSH){
						singleLettersMiddle(word1,word2,middle);
						word1 = "";
						word2 = "";
						middlefound = false;
						break;
					}
					else if(aterator.current().getStatus() == Status.MIDDL){
						singleLettersMiddle(word1,word2,middle);
						middle = aterator.current();
						word1 = word2;
						word2 = "";
						break;
					}
					else if(aterator.current().getStatus() == Status.LETTR){
						word2 = word2.concat(aterator.current().getLetter().toString());
					}
				}
				continue;
			}
			
			while(aterator.hasNext()){
				aterator.next();
				if(aterator.current().getStatus() == Status.LETTR){
					break;
				}
			}
			
			while(aterator.hasNext()){
				if(aterator.current().getStatus() == Status.MIDDL){
					middlefound = true;
					middle = aterator.current();
					break;
				}
				if(aterator.current().getStatus() == Status.FINSH){
					word1 = "";
					break;
				}
				word1 = word1.concat(aterator.current().getLetter().toString());
				aterator.next();
			}
		}
		if(middlefound){
			singleLettersMiddle(word1,word2,middle);
		}
	}
	
	

	
	private void singleLettersStart(String word, MetaData square){
		Collection<Letter> foundletters = new ArrayList<Letter>();
		Collection<String> subdictionary = ScrabbleDictionary.matchesRegex("." + word);
		if(numberofblanks > 0){
			for(String foundword : subdictionary){
				foundletters.add( Letter.CharAsLetter(foundword.charAt(0)));
			}
		}
		else{
			for(Letter letter: nonblankletters){
				String newword = Character.toString(Letter.LetterAsChar(letter)) + word;
				for(String dictword: subdictionary){
					if( dictword.matches(newword)){
						foundletters.add(letter);
					}
				}
			}
		}
		square.addOneCharMoves(foundletters);
		square.setUnaryScore(Letter.pointsPerWord(word));
	}

	private void singleLettersFinish(String word, MetaData square){
		Collection<Letter> foundletters = new ArrayList<Letter>();
		Collection<String> subdictionary = ScrabbleDictionary.matchesRegex( word + ".");
		if(numberofblanks > 0){
			for(String foundword : subdictionary){
				foundletters.add( Letter.CharAsLetter(foundword.charAt(foundword.length()-1)));
			}
		}
		else{
			for(Letter letter: nonblankletters){
				String newword = word + Character.toString(Letter.LetterAsChar(letter));
				for(String dictword: subdictionary){	
					if( dictword.matches(newword)){
						foundletters.add(letter);
					}
				}
			}
		}
		square.addOneCharMoves(foundletters);
		square.setUnaryScore(Letter.pointsPerWord(word));
	}
	
	private void singleLettersMiddle(String word1, String word2, MetaData square){
		Collection<Letter> foundletters = new ArrayList<Letter>();
		Collection<String> subdictionary = ScrabbleDictionary.matchesRegex( word1 + "." + word2 );
		for(Letter letter : nonblankletters){
			String newword = word1 + Character.toString(Letter.LetterAsChar(letter)) + word2;
			for(String dictword : subdictionary){
				if( dictword.matches(newword) ){
					foundletters.add(letter);
				}
			}
		}
		square.addOneCharMoves(foundletters);
		square.setUnaryScore(Letter.pointsPerWord(word1) + Letter.pointsPerWord(word2));
	}

	
	//=================================Stage 3==========================================
	//=================================Stage 3==========================================
	//=================================Stage 3==========================================
	//=================================Stage 3==========================================TODO
	//need to find all the valid moves, maybe set up another interface type, so only the opposing orthagonal is checked rather than the same.

	
	private List<ScrabbleMove> ListofMoves = new ArrayList<ScrabbleMove>();
	
	public void calculateFullMoves(){		
		for(int rownumber = 0 ; rownumber <= maxindex ; rownumber++){
			WordsOnAChord asdf = new WordsOnAChord( rowForwards(rownumber), nonblankletters, numberofblanks);
			for(BoardSegmentChecker segment: asdf.bites){
				for(LocalFoundWord foundword: segment.getFoundWords()){
					ScrabbleMove newmove = new ScrabbleMove(false,	rownumber	,	segment.getNibbles().get(0).index	,	foundword.score	, 	foundword.word);
					ListofMoves.add(newmove);
				}
			}
		}
		for(int colnumber = 0 ; colnumber <= maxindex ; colnumber++){
			WordsOnAChord asdf = new WordsOnAChord( colForwards(colnumber), nonblankletters, numberofblanks);
			for(BoardSegmentChecker segment: asdf.bites){
				for(LocalFoundWord foundword: segment.getFoundWords()){
					ScrabbleMove newmove = new ScrabbleMove(true,	colnumber	,	segment.getNibbles().get(0).index	,	foundword.score	, 	foundword.word);
					ListofMoves.add(newmove);
				}
			}
		}
		Collections.sort(ListofMoves);
	}
	public List<ScrabbleMove> getMovesList(){
		return ListofMoves;
	}
	
	//=================================Tests=============================================
	//=================================Tests=============================================TODO
	//=================================Tests=============================================
	//=================================Tests=============================================
	
	public void test4(){
		printTest("test 4 - the Final test, List of words baybeh!!!");
		for(ScrabbleMove move: getMovesList()){
			move.printMove();
		}
		printTest("test complete");
	}

	public void test3(){// Prints all the valid one char moves for vertical and horizontal
		printTest("test 3");
		println("printing all the valid one char moves for verticals:");
		String[][] boardbuffer = new String[15][15];
		for(int i = 0; i <= maxindex ; i++){
			for(int j = 0; j <= maxindex ; j++){
				boardbuffer[i][j] = "";
			}
		}
		for(int i = 0; i <= maxindex ; i++){
			int j =0;
			for(MetaData asdf: colForwards(i)){
				for(Letter singleletter : asdf.oneCharMoves()){
					boardbuffer[i][j] = boardbuffer[i][j].concat(Character.toString(Letter.LetterAsChar(singleletter)));					
				}
				j++;
			}
		}
		
		for(int i = 0; i <= maxindex ; i++){
			for(int j = 0; j <= maxindex ; j++){
				print("[");
				print(boardbuffer[j][i]);
				int blanks = (nonblankletters.size()-boardbuffer[j][i].length());
				for( int spaces = 0; spaces <= blanks ; spaces++ ){
					print(" ");
				}
				print("]");
			}
			println("");
		}
		
		println("printing all the valid one char moves for horizontals:");
		for(int i = 0; i <= maxindex ; i++){
			for(MetaData asdf: rowForwards(i)){
				int remaining = nonblankletters.size();
				print("[");
				for(Letter singleletter : asdf.oneCharMoves()){
					remaining--;
					print(singleletter);
				}
				for(;remaining >= 0; remaining--){
					print(" ");
				}
				print("|");
				print(asdf.getUnaryScore());
				print("]");
			}
			println("");
		}
	}
	
	public void test2(){
		printTest("Test2 - testing the markings by row");
		for(int i=0; i <= maxindex ; i++){
			for(MetaData asdf: rowForwards(i)){
				print(asdf.getStatus());
				print(" | ");
			}
			println("");
		}
		printTest("Testing markings by col");
		String[][] statbuffer = new String[15][15];
		for(int i=0; i <= maxindex ; i++){
			int j=0;
			for(MetaData asdf: colForwards(i)){
				statbuffer[i][j] = asdf.getStatus().toString();
				j++;
			}
		}
		for(int i = 0; i <= maxindex; i++){
			for(int j = 0; j <= maxindex ; j++){
				print(statbuffer[j][i].toString());
				print(" | ");
			}
			println("");
		}
	}
	
	public void test1_1(){
		printTest("Test1 - The iterators");
		println("rowForwards");
		for(int i=0; i <= maxindex ; i++){
			for(MetaData square: rowForwards(i)){
				print(square.getScoreBonus());
			}
			println("");
		}
	}
	
	//Testing the iterators in various ways.
	public void test1(){
		printTest("Test1 - The iterators");
		println("rowForwards");
		for(int i=0; i <= maxindex ; i++){
			for(MetaData square: rowForwards(i)){
				print(square.getLetter());
			}
			println("");
		}
		print(numberofblanks);
	}

	//==========================end tests========================================TODO
	//==========================words board mechanical parts===========================

	
	private void initMetaBoard(){//TODO
		for(int i=0; i <= maxindex ; i++){
			markFullCol(i);
			markFullRow(i);
		}
		for(int i=0; i <= maxindex ; i++){
			calculateStartSingleMoves(rowForwards(i));
			calculateEndingSingleMoves(rowBackwards(i));
			calculateStartSingleMoves(colForwards(i));
			calculateEndingSingleMoves(colBackwards(i));
			calculateMiddleSingleMoves(rowForwards(i));
			calculateMiddleSingleMoves(colForwards(i));
		}
	}
	
	public void readBoard(Letter[][] input){
		for(int row = 0; row <= height - 1; row++){
			for(int col = 0; col <= width - 1; col++){
				board[row][col].setLetter(input[row][col]);
			}
		}
	}
	
	//Sets up the board's unique squares.
	private WordsSquare[][] WordsWFriendsArray(){
		WordsSquare[][] board = new WordsSquare[15][15];
		for( int row = 0; row <= height - 1 ; row++){
			for (int column =0; column <= width - 1 ; column++){
				board[row][column] = new WordsSquare(ScoreBonus.__);
			}
		}
		//triple words
		Mirror(board,3,0,ScoreBonus.TW);
		
		//triple letters
		Mirror(board,0,6,ScoreBonus.TL);
		Mirror(board,3,3,ScoreBonus.TL);
		Mirror(board,5,5,ScoreBonus.TL);
		
		//double words
		Mirror(board,5,1,ScoreBonus.DW);
		Mirror(board,3,7,ScoreBonus.DW);
		
		//double letters
		Mirror(board,1,2,ScoreBonus.DL);
		Mirror(board,4,2,ScoreBonus.DL);
		Mirror(board,6,4,ScoreBonus.DL);
		
		return board;
	}
	
	
	
	/*
	 * Mirror is a private class thats designed to make it more convenient and clear code-wise to mirror squares across
	 * the four corners of the board.
	 * @param board
	 * @param row
	 * @param column
	 * @param multiplier
	 * @param wholeword
	 */
	
	private void Mirror(
			//input params
		WordsSquare[][] board,
		int row,
		int column,
		ScoreBonus type
		//-----end input params
	)
	{
		int ROWS =board.length-1;
		int COLUMNS = board[0].length-1;	
		
		board[row][column].setScoreBonus(type);
		board[ROWS - row][COLUMNS - column].setScoreBonus(type);
		board[ROWS -row][column].setScoreBonus(type);
		board[row][COLUMNS-column].setScoreBonus(type);
		board[column][row].setScoreBonus(type);
		board[COLUMNS - column][ROWS - row].setScoreBonus(type);
		board[column][ROWS -row].setScoreBonus(type);
		board[COLUMNS-column][row].setScoreBonus(type);
	}
	
	public WordsBoard(){
		board = WordsWFriendsArray();
	}
	
	public WordsBoard(Letter[][] array, Collection<Letter> availableletters){
		board = WordsWFriendsArray();
		readBoard(array);
		this.availableletters = availableletters;
		List<Letter> letterstemp = new ArrayList<Letter>(availableletters);
		for(int i = 0; i < letterstemp.size() ; i++){
			if( letterstemp.get(i) == Letter.x ){
				letterstemp.remove(i);
				this.numberofblanks++;
			}
		}
		this.nonblankletters = letterstemp;
		initMetaBoard();
	}
	
	public int width() {
		return board.length;
	}
	
	public int height() {
		return board[0].length;
	}
	
	public ScoreBonus getModifier(int row, int column){
		return board[row][column].getScoreBonus();
	}
	
	public void placeCharacter(int row, int column, char letter) { //TODO 
		board[row][column].setLetter(letter);
	}
	
	public Letter getLetter (int row, int column){
		return board[row][column].getLetter();
	}
	
}
