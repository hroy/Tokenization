
/**
 * 
 * @author H. Roy
 *
 */
public class WordWithFrequency {

	public String word = "";
	public long frequency = 0;
	
	/**
	 * set the frequency of a word
	 * @param word
	 * @param frequency
	 */
	public void setFrequency(String word, long frequency)
	{
		this.word = word;
		this.frequency = frequency;
	}
	
	/**
	 * get the frequency of a word
	 * @return
	 */
	public Long getFrequency()
	{
		return this.frequency;
	}
	
	/**
	 * get the word
	 * @return
	 */
	public String getWord()
	{
		return this.word;
	}
}
