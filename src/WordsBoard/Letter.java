package WordsBoard;

public enum Letter {
	A ('A'),B('B'),C('C'),D('D'),E('E'),F('F'),G('G'),H('H'),
	I('I'),J('J'),K('K'),L('L'),M('M'),N('N'),O('O'),P('P'),
	Q('Q'),R('R'),S('S'),T('T'),U('U'),V('V'),W('W'),X('X'),
	Y('Y'),Z('Z'),_(' '),x('?');
	
	Letter(char letter){
	}
	
	protected static Letter CharAsLetter(char letter){
		switch(letter){
		case 'A':
			return Letter.A;
		case 'B':
			return Letter.B;
		case 'C':
			return Letter.C;
		case 'D':
			return Letter.D;
		case 'E':
			return Letter.E;
		case 'F':
			return Letter.F;
		case 'G':
			return Letter.G;
		case 'H':
			return Letter.H;
		case 'I':
			return Letter.I;
		case 'J':
			return Letter.J;
		case 'K':
			return Letter.K;
		case 'L':
			return Letter.L;
		case 'M':
			return Letter.M;
		case 'N':
			return Letter.N;
		case 'O':
			return Letter.O;
		case 'P':
			return Letter.P;
		case 'Q':
			return Letter.Q;
		case 'R':
			return Letter.R;
		case 'S':
			return Letter.S;
		case 'T':
			return Letter.T;
		case 'U':
			return Letter.U;
		case 'V':
			return Letter.V;
		case 'W':
			return Letter.W;
		case 'X':
			return Letter.X;
		case 'Y':
			return Letter.Y;
		case 'Z':
			return Letter.Z;
		case ' ':
			return Letter._;
		case '.':
			return Letter.x;
		default:
			System.out.print("WordsBoard tried converting char to Letter, but failed, the character being converted was:'");
			System.out.print(letter);
			System.out.println("'");
			throw new IllegalStateException();
		}
	}
	
	protected static char LetterAsChar(Letter letter){
		switch (letter){
		case A:
			return 'A';
		case B:
			return 'B';
		case C:
			return 'C';
		case D:
			return 'D';
		case E:
			return 'E';
		case F:
			return 'F';
		case G:
			return 'G';
		case H:
			return 'H';
		case I:
			return 'I';
		case J:
			return 'J';
		case K:
			return 'K';
		case L:
			return 'L';
		case M:
			return 'M';
		case N:
			return 'N';
		case O:
			return 'O';
		case P:
			return 'P';
		case Q:
			return 'Q';
		case R:
			return 'R';
		case S:
			return 'S';
		case T:
			return 'T';
		case U:
			return 'U';
		case V:
			return 'V';
		case W:
			return 'W';
		case X:
			return 'X';
		case Y:
			return 'Y';
		case Z:
			return 'Z';
		case _: //this is an unoccupied tile;
			return ' ';
		case x: //blank tile (lowercase x)
			return '.';
		default: throw new IllegalStateException();
		}
	}
	
	protected static int pointsPerWord(String string ){
		char[] word = string.toCharArray();
		int score = 0;
		for(char letter : word){
			score += pointsPerLetter( CharAsLetter(letter));
		}
		return score;
		
	}
	
	protected static int pointsPerLetter ( Letter letter ){ //warning, does not check if input is alphanumeric TODO add an exception? 
		switch (letter){
		case A:
			return 1;
		case B:
			return 2;
		case C:
			return 4;
		case D:
			return 2;
		case E:
			return 1;
		case F:
			return 4;
		case G:
			return 3;
		case H:
			return 4;
		case I:
			return 1;
		case J:
			return 10;
		case K:
			return 4;
		case L:
			return 2;
		case M:
			return 4;
		case N:
			return 3;
		case O:
			return 1;
		case P:
			return 4;
		case Q:
			return 10;
		case R:
			return 1;
		case S:
			return 1;
		case T:
			return 1;
		case U:
			return 2;
		case V:
			return 5;
		case W:
			return 4;
		case X:
			return 8;
		case Y:
			return 2;
		case Z:
			return 10;
		case _: //this is an unoccupied tile;
			return 0;
		case x:
			return 0;//blank tile (lowercase x)
		default: throw new IllegalStateException();
		}
	}
}
