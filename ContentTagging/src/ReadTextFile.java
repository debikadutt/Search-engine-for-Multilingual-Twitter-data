
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Reads data line by line from a given file.
 * 
 * @author Alizishaan Khatri
 *
 */
public class ReadTextFile {
	
	private Scanner inp_file;
	
	/**
	 * To prevent blank instantiation
	 */	
	@SuppressWarnings("unused")
	private ReadTextFile(){
	}
	
	/**
	 * Initializes an instance with a given filename
	 *  
	 * @param fil_nam Complete path of the entered filename
	 */
	public ReadTextFile(String fil_nam){
		
		//Checks if file path is valid
		try{
			//Create a Scanner instance to read from a given file
			inp_file=new Scanner(new File(fil_nam));
		}catch(FileNotFoundException e){
			//Case where file path is invalid			
			System.err.println("Invalid file "+fil_nam);			
		}
	}
	
	/**
	 * Checks if the given file has any more data to be read
	 * 
	 * @return true If file has unread data<br>
	 * false If EOF condition has been reached
	 * @throws IllegalStateException If this file has been closed
	 */
	public boolean hasNext() throws IllegalStateException{
		return(inp_file.hasNext());
	}
	
	/**
	 * Advances this scanner past the current line and returns the input that was skipped.<br>
	 * This method returns the rest of the current line, excluding any line separator at the end.<br>
	 * The position is set to the beginning of the next line. Since this method continues to <br>
	 * search through the input looking for a line separator, it may buffer all of the input <br>
	 * searching for the line to skip if no line separators are present.<br>
	 * 
	 * @throws NoSuchElementException - if no line was found
	 * @throws IllegalStateException - if this file is closed
	 * @return The line that was skipped
	 */
	public String nextLine() throws NoSuchElementException,IllegalStateException{
		return(inp_file.nextLine());
	}
	
	/**
	 * Tests whether the file denoted by this abstract pathname is a normal file.<br> 
	 * A file is normal if it is not a directory and, in addition, satisfies other system-dependent criteria.<br>
	 * Any non-directory file created by a Java application is guaranteed to be a normal file. 
	 * 
	 * @param file_name Complete path of the file along with location
	 * @return true-If and only if the path represents a normal system File.<br>
	 * false-in all other cases
	 */
	public boolean fileExists(String file_name){
		return(new File(file_name).isFile());
	}
	
	/**
	 * Closes the current file. Attempting to read from this file after this operation will result in <br>
	 * an IllegalStateException.
	 * 
	 */
	public void close(){
		inp_file.close();
	}
}
