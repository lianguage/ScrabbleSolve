package Permutations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Permutation<E> implements Iterable<E>{

	List<E> options;
	int choices;
	int[] indexarray;
	
	final int N = 12;
	
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Permutation( Collection<E> options, int choices){
		this.options = new ArrayList(options);
		this.choices = choices;
		assert options.size() > choices;
		
		indexarray = new int[choices];

	}
	
	
	
	private void incrementIndexarray(){
		
	}
	
	private void QuickPerm() {
		   int a[] = new int[N];
		   int p[] = new int[N];
		   int i, j, tmp; // Upper Index i; Lower Index j

		   for(i = 0; i < N; i++) {  // initialize arrays; a[N] can be any type
		      a[i] = i + 1;   // a[i] value is not revealed and can be arbitrary
		      p[i] = 0;       // p[i] == i controls iteration and index boundaries for i
		   }
		   //display(a, 0, 0);   // remove comment to display array a[]
		   i = 1;   // setup first swap points to be 1 and 0 respectively (i & j)
		   while(i < N) {
		      if (p[i] < i) {
		         j = i % 2 * p[i];   // IF i is odd then j = p[i] otherwise j = 0
		         tmp = a[j];         // swap(a[j], a[i])
		         a[j] = a[i];
		         a[i] = tmp;
		         //display(a, j, i); // remove comment to display target array a[]
		         p[i]++;             // increase index "weight" for i by one
		         i = 1;              // reset index i to 1 (assumed)
		      } else {               // otherwise p[i] == i
		         p[i] = 0;           // reset p[i] to zero
		         i++;                // set new index value for i (increase by one)
		      } // if (p[i] < i)
		   } // while(i < N)
		} // QuickPerm()

}
