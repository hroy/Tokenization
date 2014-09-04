
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author H. Roy
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String inputFilesDirectory ="";
		long startTime = System.currentTimeMillis();
		
		if (args.length != 1) {
            System.err.println("Please provide the directory location...");
            //System.exit(1);
        }
		
		if(args.length!=0)inputFilesDirectory = args[0];
		while (!new File(inputFilesDirectory).isDirectory() && !inputFilesDirectory.equalsIgnoreCase("exit")) {
			System.err.println("Sorry! It is not a directory. Please enter the directory now...");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				inputFilesDirectory = br.readLine();			
				while(inputFilesDirectory == "")
				{
					inputFilesDirectory = br.readLine();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
		if(inputFilesDirectory.equalsIgnoreCase("exit")) System.exit(1);
		
		System.out.println("Given input directory: "+inputFilesDirectory);
		
		Tokenizer tokenizer = new Tokenizer();
		File[] listFiles = tokenizer.readFiles(inputFilesDirectory);	
		
		if (listFiles.length == 0) {
            throw new RuntimeException("No file in this directory!");
        }		
		
		for (File file : listFiles) {
            try {
            	if(file.isFile())tokenizer.tokenizeFile(file);
            } catch (Exception ex) {
                System.err.println("Exception in file reading! - " + file.getName() + ", ex- "+ex.getMessage());
            }
        }
		
//		System.out.println("End file reading.");
		
		tokenizer.showStatistics();
		long durationTime = System.currentTimeMillis() - startTime;
		System.out.println("Total running time: " + durationTime + " msec");
	}
}
