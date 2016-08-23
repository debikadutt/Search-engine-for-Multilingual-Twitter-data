import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Write JSON Data to a file
 * @author Alizishaan Khatri
 *
 */
public class WriteJSON implements Closeable{
	
	private PrintWriter out_file;
	
	/**
	 * Constructor creates output file
	 */
	public WriteJSON(String out_fil){
		
		try{
			out_file=new PrintWriter(new File(out_fil),"UTF-8");
		}catch( FileNotFoundException e){
			System.err.println("Error generating output file");
			System.exit(1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		out_file.println("[");
	}
	
	/**
	 * Add translated input to JSON array and write result to output
	 * @param inp
	 */
	public void processJSONArray(JSONArray inp) throws Exception{
		
		//Get size of JSON array
		final int JSON_size=inp.length();
		
		//Iterate through all JSON objects in the entered array
		for(int i=0;i<JSON_size;i++){
			
			//Read tweet
			JSONObject j_obj=(JSONObject) inp.get(i);
			
			boolean isSent=false;
			
			while(!isSent){
				
				try{
				String response=SentimentTagger.sendQuery(j_obj);
				isSent=true;
				
				//use next API key on getting an error response
				if(response.equalsIgnoreCase("ERRORdaily-transaction-limit-exceeded")||response.equalsIgnoreCase("ERRORinvalid-api-key")){
					
					SentimentTagger.API_key=SentimentTagger.API_list.pop();
					isSent=false;
				}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			
	
			
			//Read the text contained in the tweet
					
			
			/* Translate the tweet to ALL languages */
			/*for(int j=0;j<langs.length;j++){
				
				//Translate only if current language is different from the language of the Tweet
				if(my_lang.compareTo(langs[j])!=0){
					
					String temp=Yandex_API.sendQuery(my_text, my_lang, langs[j]);
					//System.out.println(my_lang+"to"+langs[j]+"response is"+temp);
					
					j_obj.append("text_"+langs[j],temp);					
				}
			}*/
			//count++;
			out_file.println(j_obj.toString()+",");
		}
		
		//System.out.println("Count = "+count);
	}	

	@Override
	public void close() throws IOException {
		out_file.println("]");
		out_file.close();		
	}	
	
}
