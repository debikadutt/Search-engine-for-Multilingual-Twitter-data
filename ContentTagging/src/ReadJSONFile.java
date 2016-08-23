import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.json.*;

/**
 * 
 */

/**
 * Reads JSON Data from file
 * 
 * @author Alizishaan Khatri
 *
 */
public class ReadJSONFile {
	
	//Variables for internal use only
	private File inp_file;
	private Scanner s;
	
	/**
	 * Stores the input file path.<br> 
	 * Also, check if path points to a valid file. 
	 * 
	 * @param f Path of input file
	 */
	public ReadJSONFile(String f){
		
		try{
		inp_file=new File(f);
		s=new Scanner(inp_file);		
		
				
		}catch(FileNotFoundException s){
			System.err.println("Invalid Input File path");
			System.exit(1);
		}	
		
	}
	
	/**
	 * 
	 * Gets JSON array from input file
	 * 
	 * @return JSON Array read from Input file
	 * 
	 */	
	public JSONArray getJSONArr(){
		
		StringBuffer JSONArr=new StringBuffer();
		int count=0;
		//Write all the data from the file to a single String
		while(s.hasNext()){
			JSONArr.append(s.next()+" ");
			count+=1;
		}
		System.out.println("count="+count);
		//Create JSON Array object from StringBuffer response
		JSONArray file_contents=new JSONArray(JSONArr.toString());
		
		//Close input file
		s.close();
		
		//Close file
		return(file_contents);
	}
}
