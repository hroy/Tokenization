
import java.util.TreeSet;

/**
 * 
 * @author H. Roy
 *
 */
public interface Words {

	/**
	 * get total number of tokens
	 * @return
	 */
	long countTotalTokens();
	/**
	 * count the unique words
	 * @return
	 */
	long countUniqueWords();
	/**
	 * count the single occuring words
	 * @return
	 */
	long countSingleOccuringWords();
	/**
	 * get the given number of most frequent words
	 * @param number
	 * @return
	 */
	TreeSet<WordWithFrequency> getMostFrequentWordsWithFrequency(int number);
	/**
	 * get the average number of tokens
	 * @return
	 */
	double getNumberOfAverageTokens();
}
