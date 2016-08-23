/**
 * 
 */

import java.io.DataOutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.JSONObject;
import javax.net.ssl.HttpsURLConnection;

/**
 * Wrapper for the Yandex Translation API
 * 
 * @author Alizishaan Khatri
 *
 */
public class Yandex_API {
	
private final static String API_key="trnsl.1.1.20151211T053517Z.01be4c2cc9c95a62.36dae5c06316ebe910bedbff36000fcf4b2f109b";
//used up: "trnsl.1.1.20151212T211035Z.4b789a0993bdaf81.f53df626876c2b377a4272667ea3a33809e695dc";
//"trnsl.1.1.20151211T053517Z.01be4c2cc9c95a62.36dae5c06316ebe910bedbff36000fcf4b2f109b";
//"trnsl.1.1.20151212T020240Z.4b4ecbc6ec9524cc.bae51bf1bd3847de088cc323824462c9180c42fb";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		
		//Specify complete address of working directory
		String work_dir="C:\\My Stuff\\CSE 535\\Project 3\\ruhan\\";
		
		//Specify filename of input file
		ReadTextFile fil=new ReadTextFile(work_dir+"files.txt");
		
		while(fil.hasNext()){
			
			String curr_fil=work_dir+fil.nextLine();
			
			if(fil.fileExists(curr_fil)){
				
				ReadJSONFile f=new ReadJSONFile(curr_fil);
				WriteJSON w=new WriteJSON(curr_fil+"_output.json");
				w.processJSONArray(f.getJSONArr());
				w.close();
				
			}else{
				System.out.println("Invalid file:"+curr_fil);
			}		
		}
	}	
	
	/**
	 * Gets translated output from the Yandex API by using a POST request
	 * 
	 * @return String The translated value of the input<br>
	 * If unsuccessful, returns the error code returned by the server.
	 * 
	 * @param text The string to be translated
	 * @param fromLang Specify code of the source language
	 * @param toLang Specify code of the destination language
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ProtocolException
	 */
	public static String sendQuery(String text, String fromLang,String toLang) throws Exception {
		
		//API URL
		String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
		
		//Build language String
		String lang=fromLang+"-"+toLang;
		
		//Specify parameters
		String params=
				"key="+ URLEncoder.encode(API_key,"UTF-8")+
				 "&text="+URLEncoder.encode(text,"UTF-8")+
				 "&lang="+URLEncoder.encode(lang,"UTF-8");
		
		//Add parameters to query URL
		url=url+params;			
		
		//Create URL from String
		URL obj = new URL(url);
		
		//Create connection to the above URL
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//Configure connection to use POST
		con.setRequestMethod("POST");
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		
		//wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		
		//Get response code from server
		int responseCode = con.getResponseCode();
		
		//Create Scanner object to read server response
		Scanner in = new Scanner(con.getInputStream());
		StringBuffer response = new StringBuffer();
		
		//Read all values sent by server
		while (in.hasNext()) {
			String inputLine=in.nextLine();
			//System.out.println(inputLine);
			response.append(inputLine);
		}
		
		//Close Scanner object
		in.close();
		
		//Get String representation of StringBuffer
		String res=response.toString();
		
		if(responseCode==200){
		
		//Parse Response
		JSONObject API_response=new JSONObject(res);
		String API_resp=API_response.getJSONArray("text").toString();
		
		//Parse and return the extracted JSON Array
		int str_len=API_resp.length();
		return(API_resp.substring(2, str_len-2));
		
		}else{
			//return error code
			String error="Error code: "+responseCode;
			System.err.println(error);
			System.exit(-1);
			return(error);
		}				
	}
}
