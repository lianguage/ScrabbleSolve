package WordsBoard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * class made to read boards.
 * @author Liang
 *
 */


public class BoardReader {
	
	public static Letter[][] fromFile(String filehandle){
		Letter[][] boardinit = new Letter[15][15];
		
		Scanner sc = null;
		
		try {
			sc = new Scanner( new File(filehandle));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		{//{
		int i = 0;
		while(sc.hasNextLine()){
			
			char[] rowtemp = sc.nextLine().toUpperCase().toCharArray();
			Letter[] row = new Letter[15];
			
			{//{
			int j=0;
			for(char letter: rowtemp){
				if(letter != '\t'){
					row[j] = Letter.CharAsLetter(letter);
					j++;
				}
			}}
			boardinit[i] = row;
			i++;
		}}
		
		return boardinit;
	}
}