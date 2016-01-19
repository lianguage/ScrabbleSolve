package Dictionary;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import Combinations.Combination;
import Combinations.CombinationGenerator;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


/**
 * 
 * @author Liang_2
 */


public class MultiMapDictionary implements JumbleDictionary{
	
	private static Multimap<String, String> wordmap = ArrayListMultimap.create();
	private Factory Factory= new Factory();
	String currentdir = getClass().getCanonicalName();
	
	public MultiMapDictionary(){
		readWordFile("C:\\Users\\liang\\Dropbox\\Workspace\\ScrabbleSolver\\words.txt");
	}
	
	private class Factory{
		private <T> List<T> someList(){
			return new ArrayList<T>();
		}
		public <T> List<T> someList(Collection<T> items){
			return new ArrayList<T>(items);
		}
		private <T extends Comparable<T>> Combination<T> makeCombo(  ){
			return new CombinationGenerator<T>( );
		}
	}
	
	/**
	 * 
	 * Initialises the dictionary from a text file. Keys are the words sorted in alphabetical order. Sometimes multiple words will be assigned to a key, and these are anagrams.
	 * @param wordsfile
	 */
	private static void readWordFile(String wordsfile){
		Scanner sc = null;
		try {
			sc = new Scanner(new File(wordsfile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(sc.hasNext()){
			String word = sc.next();
			String sorted = sortLetters(word);
			wordmap.put( sorted, word );
		}	
	}

	/**
	 * sortLetters sorts all the characters of a string. 
	 * It should only be used with individual words, because it will also remove all non-alphabetic characters from the string
	 * @param word
	 * @return
	 */
	private static String sortLetters(String word){
		word.replaceAll("[^a-zA-Z]", "");
		word.replaceAll("[A-Z]", "[a-z]");
		char[] letters = word.toCharArray();
		Arrays.sort(letters);
		return new String(letters);
	}

	/**
	 * 
	 * The anagram(String word) method is intended as the primary way of accessing the data in a Dictionairy.
	 * it takes any combo of letters, whether it was originally a word or not, and returns a Collection of strings
	 * that are also words in the dictionary that contain those letters.
	 * @param word
	 * @return
	 */
	public Collection<String> Anagram(String word){
		String letters = sortLetters(word);
		if(wordmap.containsKey(letters)){
			return wordmap.get(letters);
		}
		else{
			return Collections.emptyList();
		}
	}
	
	public Collection<String> Target(String word){
		
		String letters = sortLetters(word);
		
		List<String> letterarray = Factory.someList(Arrays.asList(letters.split("")));			
		letterarray.remove(0); //remove the empty string at the start of the List
		
		int noofletters = letters.length();
		
		Combination<String> Combination = Factory.makeCombo();
		Collection<String> foundwords = Factory.someList();//initially empty
		for( int choosefrom = 1 ; choosefrom <= noofletters ; choosefrom++ ){ 

			
			Combination.initialise(letterarray, choosefrom);  //Set up the combination for 'choosefrom' number of words
			while(Combination.hasNextCombo()){
				Collection<String> charSubLetters = Combination.getCombo();				
				String stringSubLetters = "";
				for(String appendedLetter : charSubLetters){
					stringSubLetters = appendedLetter.concat(stringSubLetters);
				} // join letters back to single string
				for(String addedword :wordmap.get(stringSubLetters)){
					foundwords.add(addedword);
				} //search for words that can be formed using that string, and add them to found words.
			}
		}
		return foundwords;
	}
	
	public boolean containsWord (String word){
		return wordmap.containsValue(word);
	}
}
