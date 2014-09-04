
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author H. Roy
 *
 */
public class Tokenizer implements Words{

	private String collectionDirectory;
	private long totalNumberOfFiles = 0;
	
	private long totalTokens = 0;
	private HashMap<String, Long> terms = new HashMap<String, Long>();
	
	//Check in whole file
    private Pattern tag = Pattern.compile("<.*>");
    private Pattern puncPattern = Pattern.compile("[,|\\-|\\\\|=|\\\"|@|{|}|#|%|+|~|\\*|;|:|\\[|\\]|?|/|\\(|\\)|\\|]");
    private Pattern fullStopPattern = Pattern.compile("[\\.]");
    
    //Check in each token
    private Pattern exclaPattern = Pattern.compile("!$");
    private Pattern aposPattern = Pattern.compile("'s$");
    private Pattern pluralPattern = Pattern.compile("[']+");
    
    private Pattern nonAlphaPattern = Pattern.compile("[^a-zA-Z]+"); //including numbers
    
    /**
     * set the input directory
     * @param directory
     */
	public void setCollectionDirectory(String directory) {
        collectionDirectory = directory;
    }
	
	/**
	 * list the files from input directory
	 * @return
	 */
	public File[] readFiles() {
        File dr = new File(collectionDirectory);
        File[] files = dr.listFiles();  
        
        return files;
    }
	
	/**
	 * list the files from input directory
	 * @param directory
	 * @return
	 */
	public File[] readFiles(String directory) {
		this.collectionDirectory = directory;
        File dr = new File(directory);        
        File[] files = dr.listFiles();   
        
        return files;
    }
	
	/**
	 * get the total number of files in input directory
	 * @return
	 */
	public long getTotalNumberFiles()
	{
		return totalNumberOfFiles;
	}
	
	/**
	 * tokenize a given file
	 * @param file
	 * @throws FileNotFoundException
	 */
	public void tokenizeFile(File file) throws FileNotFoundException
	{
		String str = new Scanner(file).useDelimiter("\\Z").next();
		
//		System.out.println("in- " + str);
		
		str = tag.matcher(str).replaceAll("");
		str = puncPattern.matcher(str).replaceAll(" ");
		str = fullStopPattern.matcher(str).replaceAll("");
		
//		System.out.println("out- " + str);
		
		String[] tokens = getTokens(str);
        if (tokens.length != 0) {
            totalTokens += tokens.length;
            
            for (String token : tokens) {
                if (!terms.containsKey(token)) {
                    terms.put(token, (long)1);
                } else {
                    terms.put(token, terms.get(token) + 1);
                }
            }
        }
        
        totalNumberOfFiles++;
	}
	
	/**
	 * split into tokens a given string
	 * @param str
	 * @return
	 */
	public String[] getTokens(String str) {
        
        String strArr[] = str.split("\\s+");

        ArrayList<String> tokens = new ArrayList<String>();
        for (String t : strArr) {
        	//'s, ' and ! removal
        	t = exclaPattern.matcher(t).replaceAll("");
        	t = aposPattern.matcher(t).replaceAll("");   
        	t = pluralPattern.matcher(t).replaceAll("");
            //non-alpha including numbers removal
            if (nonAlphaPattern.matcher(t).find()) {
                continue;
            }            
            if(t.length() > 0)
            {
            	tokens.add(t.toLowerCase());
            }
        }
        
        return tokens.toArray(new String[tokens.size()]);
    }	

	/**
	 * return the statistics parsing all files in the given input directory
	 */
	public void showStatistics()
	{
		System.out.println("1. Number of tokens: " + countTotalTokens());
        System.out.println("2. Number of unique words: " + countUniqueWords());
        System.out.println("3. Number of words that occur only once: " + countSingleOccuringWords());
        
        System.out.println("4. 30 most frequent words-");
        TreeSet<WordWithFrequency> top30Words = getMostFrequentWordsWithFrequency(30);
        Iterator<WordWithFrequency> top30WordsIterator = top30Words.descendingIterator(); 
//        Iterator<WordWithFrequency> top30WordsIterator = top30Words.iterator(); 
        
        System.out.println("Word\tCount");
        while (top30WordsIterator.hasNext()) {
        	WordWithFrequency listItem = top30WordsIterator.next();
            System.out.println(listItem.getWord() +"\t"+ listItem.getFrequency());
        }
        
        System.out.println("5. Average number of word tokens per document: " + getNumberOfAverageTokens());
	}
	
//	@Override
	public long countTotalTokens() {
		// TODO Auto-generated method stub
		return this.totalTokens;
	}

//	@Override
	public long countUniqueWords() {
		// TODO Auto-generated method stub
		return this.terms.size();
	}

//	@Override
	public long countSingleOccuringWords() {
		// TODO Auto-generated method stub
		long count = 0;
        for (String term : terms.keySet()) {
            if (terms.get(term) == 1) {
                count++;
            }
        }
        return count;
	}

//	@Override
	public TreeSet<WordWithFrequency> getMostFrequentWordsWithFrequency(int number) {
		// TODO Auto-generated method stub		
		TreeSet<WordWithFrequency> mostFrequentWords = new TreeSet<WordWithFrequency>(new Comparator<WordWithFrequency>() {

//			@Override
			public int compare(WordWithFrequency arg0, WordWithFrequency arg1) {
				// TODO Auto-generated method stub
				int cValue = arg0.getFrequency().compareTo(arg1.getFrequency());
				return (cValue==0)?1:cValue;
			}            
        });
				
		for(String term : terms.keySet())
		{
			WordWithFrequency word = new WordWithFrequency();
			word.setFrequency(term, terms.get(term));
			
//			System.out.println(term +"="+ terms.get(term) );
			
			if(mostFrequentWords.size()<number)
			{
				mostFrequentWords.add(word);
			}
			else 
			{
				if(mostFrequentWords.first().frequency < word.frequency)
				{
					mostFrequentWords.pollFirst(); 
					mostFrequentWords.add(word);
				}
			}
		}		
		
		return mostFrequentWords;
	}

//	@Override
	public double getNumberOfAverageTokens() {
		// TODO Auto-generated method stub
		return (double)countTotalTokens()/(double)getTotalNumberFiles();
	}	
	
}
