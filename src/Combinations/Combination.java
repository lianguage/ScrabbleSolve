package Combinations;
import java.util.Collection;



public interface Combination <T> {
	
	public void initialise(Collection<T> items, int choosefrom);
	public boolean hasNextCombo();
	public Collection<T> getCombo();
	public void reset();

}
