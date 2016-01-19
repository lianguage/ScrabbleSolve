package Combinations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Liang_2
 *
 * @param <T>
 */


public class CombinationGenerator<T extends Comparable<T>> implements Combination<T> {
	
	protected int[] iterators; //iterators represent an index value within an array of booleans. Values pointed to by the index are true, whilst other values are false.
					 //false values are items that have not been chosen.
	protected int k;
	protected int n;
	private boolean hasNext;
	List<T> items;
	Factory Factory = new Factory();
	
	class Factory{
		private List<T> someList(){
			return new ArrayList<T>();
		}
		
		private List<T> someList(Collection<T> items){
			return new ArrayList<T>(items);
		}
	}
	
	public CombinationGenerator(){//must use the initialise method if this constructor is used
	}
	
	public CombinationGenerator(Collection<T> collection, int choices){
		this.items = Factory.someList(collection);
		this.k = choices;
		this.n = items.size();
		if( n < 1 || k < 1 || k > n){ //invariant condition should cause an exception TODO, n and k are set to be 1 or greater, since returning an empty collection could cause problems.
			this.hasNext = false;
		}
		else{
			this.hasNext = true;
		}
		this.iterators = new int[k];
		Collections.sort(items);
		for( int i = k - 1 ; i >= 0; i--){
			this.iterators[ i ] = n-i-1;
		}
	}

	public void initialise(Collection<T> collection, int choices){
		this.items = Factory.someList(collection);
		this.k = choices;
		this.n = items.size();
		if( n < 0 || k < 0 || k > n){ //invariant condition should cause an exception TODO
			this.hasNext = false;
		}
		else{
			this.hasNext = true;
		}
		this.iterators = new int[k];
		Collections.sort(items);
		for( int i = k - 1 ; i >= 0; i--){
			this.iterators[ i ] = n-i-1;
		}
	}
	
	private void generateNextCombination(){
		int previous = -1; 
		int arrayindex = iterators.length - 1; 
		for( ; arrayindex >=0 ; ){
			
			if( iterators [ arrayindex ] == previous + 1 ){
				previous = iterators[arrayindex];
				arrayindex --;
				continue;
			}
			else if(items.get(iterators[arrayindex]).equals(items.get(iterators[arrayindex]-1))){
				iterators[arrayindex]--;
				continue;
			}
			
			previous = iterators[arrayindex];
			for(int j = 1 ; arrayindex <= iterators.length - 1 ; ){
				iterators[ arrayindex ] = previous - j ;
				arrayindex ++;
				j++;
			}
			return;
		}
		hasNext = false;
		return;
	}
	

	public boolean hasNextCombo() {
		return hasNext;
	}

	public Collection<T> getCombo(){
		Collection<T> founditems = Factory.someList();
		for( int index = 0; index <k ; index++){
			founditems.add(items.get(iterators[index] ));
		}
		generateNextCombination();
		return founditems;
	}
	
	public void reset() {
		for( int i = k - 1; i >= 0; i--){
			this.iterators[ i ] = n-i;
		}
	}
}