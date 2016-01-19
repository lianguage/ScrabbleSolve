package Dictionary;

import java.util.Collection;


public interface JumbleDictionary {
	public Collection<String> Anagram(String letters);
	public Collection<String> Target(String letters);
	public boolean containsWord (String word);
}

