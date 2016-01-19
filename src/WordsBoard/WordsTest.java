package WordsBoard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class WordsTest {
	
	public static void main(String[] args){
		
		Collection<Letter> availableletters = new ArrayList<Letter>();
		
		//addAlphabet(availableletters);
		addLetters(availableletters,"QZPOAEI");
		

		long starttime = System.nanoTime();
		WordsBoard board = new WordsBoard(BoardReader.fromFile("fiona.txt"), availableletters);
		board.calculateFullMoves();
		
		board.test1();
		board.test1_1();
		board.test2();
		board.test3();
		board.test4();
		long timeelapsed = System.nanoTime() - starttime; 
		System.out.println("Time elapsed:" + TimeUnit.SECONDS.convert(timeelapsed, TimeUnit.NANOSECONDS) + "seconds");
		
	}
	
	private static void  addLetters(Collection<Letter> letterset, String letterstring){
		char[]chararray = letterstring.toCharArray();
		for(char letter: chararray){
			letterset.add(Letter.CharAsLetter(letter));
		}
	}

}

