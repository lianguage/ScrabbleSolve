package WordsBoard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;




public class ScrabbleDictionary {
	private static List<String> dictionary = readWordFile("C:\\Users\\liang\\Dropbox\\Workspace\\ScrabbleSolver\\words.txt");
	String currentdir = getClass().getCanonicalName();
	
	public ScrabbleDictionary(){
		readWordFile("C:\\Users\\liang\\Dropbox\\Workspace\\ScrabbleSolver\\words.txt");
	}
	
	/**
	 * 
	 * Initialises the dictionary from a text file. Keys are the words sorted in alphabetical order. Sometimes multiple words will be assigned to a key, and these are anagrams.
	 * @param wordsfile
	 */
	private static List<String> readWordFile(String wordsfile){
		List<String> dictionarytemp = new ArrayList<String>();
		
		Scanner sc = null;
		try {
			sc = new Scanner(new File(wordsfile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(sc.hasNext()){
			String word = sc.next();
			dictionarytemp.add(word);
		}	
		return dictionarytemp;
	}
	
	public static boolean containsWord (String word){
		return dictionary.contains(word);
	}
	
	public static Collection<String> matchesRegex(String regex){
		Collection<String> subset = new ArrayList<String>();
		for(String word: dictionary ){
			if(word.matches( regex )){
				subset.add(word);
			}
		}
		return subset;
	}
	
}
