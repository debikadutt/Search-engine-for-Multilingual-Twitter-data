
import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.CosNaming.NamingContextPackage.NotEmpty;




public class SentimentTagger {
	
	public static LinkedList<String> API_list;
	public static String API_key;
	
	private static void setList(){
		API_list=new LinkedList<String>();
		
		API_list.add("0ef395978e06d220e1982dca53293fdcfb8b0ad6");
		API_list.add("501e01f79ba700f71592d1ead73e41fd5df72992");
		API_list.add("2e8f682f08ceb88943b2c9dc38c0871c25b4dac0");
		API_list.add("e13c8d849f273c53336f6f910fa1b33158b229c9");
		//NEW KEYS
		API_list.add("2c8d5e16f95872bc1ca54134a32c4e00702e277a");
		API_list.add("5159768c2344c9084001d6b6f679c8f00e610d0a");
		API_list.add("0bac5a8c34a233f0e82de7ceeee51eea69a6c222");
		API_list.add("28ff2026854b323ee42d66f708d8d5983e8f0fec");
		API_list.add("1e78eccb18837c49a9eccb1f073e9d9cb51ea9aa");
		API_list.add("57f0821a8bd67695c6623dfdbb3d88e5cb527d45");
		API_list.add("7ed4742aaf91a2cc1b98615a9a2a65414e1415cc");
		API_list.add("fb470ea9da73380f6aa5e6893d980d56ee80db64");
		
		API_list.add("535890d5aefe0a3ecbb16cb55ae4c0445548c19c");
		API_list.add("014df7308d7d9b16adc2f67fc0a69147c67dec86");
		API_list.add("b424da85c8189c57c04fe0d1643ddfad32769b57");
		API_list.add("c5d75c4fc37cf741aa581bbc4eac1a394df2e016");
		API_list.add("faa083dbf457364135265ca11bf0923331247324");
		API_list.add("38938d3b61820c5843ed7fbe31755b3c101d79e7");
		API_list.add("5aa8e53f9bd3fec09070e166a4c31fbb28fd46b0");
		API_list.add("04424b019cee2dac8596a9f88f673a82b2a2eee1");
		API_list.add("85605d50b6a9b73a1a923531b67b14f21e81e3ab");
		API_list.add("ada4c6153bbfa0435839776b503b747be5e61db6");
		//OLD KEYS
		API_list.add("89b593bff54f035b4a14094184777e9f3f9f0a39");
		API_list.add("9474d77941010a35bf75b0925157027849256b77");
		API_list.add("cdd2c4d731c86867ccdef5d2d12c9da2d361e5ee");
		API_list.add("a6888f0af15c33e6bdf09efae4f62291a7556da6");
		API_list.add("d1da45ede544f3a3dd300b9efca6b56ddfa9efbd");
		API_list.add("6a938c0b6ac854bff3b5e7838a21a653a2bfa75b");
		API_list.add("6db1d7a71442eddd8c103b41b9c1724e51955309");
		API_list.add("365fbc084c77c4263d041f5dfef5279091594e0a");
		
		
	}

	public static void main(String[] args) throws Exception{
		
		setList();
		
		String my_working_dir="//Users//AmmY//Documents//workspace//twitterCrawler//ruhan//";
		
		ReadTextFile fil_list=new ReadTextFile(my_working_dir+"sds.txt");
		
		while(fil_list.hasNext()){
			
			String curr_file=my_working_dir+fil_list.nextLine();
			
			if(fil_list.fileExists(curr_file)){
				
				ReadJSONFile f=new ReadJSONFile(curr_file);
				WriteJSON w=new WriteJSON(curr_file+"_1.json");
					
				w.processJSONArray(f.getJSONArr());
				w.close();
				
			}else{
				System.out.println("Invalid file: "+curr_file);
			}
		}
		}
	public static String sendQuery(JSONObject j_obj) throws Exception {
		String tweetLang=j_obj.get("tweet_lang").toString();
		String text1=j_obj.get("text_en").toString();
		if(tweetLang=="en"){
			text1=j_obj.get("text_en").toString();
		}
		if(tweetLang=="de"){
			text1=j_obj.get("text_de").toString();
		}
		if(tweetLang=="ru"){
			text1=j_obj.get("text_ru").toString();
		}
		if(tweetLang=="fr"){
			text1=j_obj.get("text_fr").toString();
		}
		URL urlObj = null;
		try {
			urlObj = new URL("http://gateway-a.watsonplatform.net/calls/text/TextGetRankedNamedEntities?apikey=" + API_key + "&text="+URLEncoder.encode(text1,"UTF-8")+"&outputMode=json");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(urlObj.toString() + "\n");

		URLConnection connection = urlObj.openConnection();
		connection.connect();

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		StringBuilder builder = new StringBuilder();
		while ((line = reader.readLine()) !=  null) {
		    builder.append(line + "\n");
		}
		System.out.println(builder);
		
		String res=builder.toString();
		
		//insert loop to go through all the tweets here
		JSONObject obj=new JSONObject(res);
		
		String API_response=(String) obj.get("status");
		
		if(API_response.equalsIgnoreCase("OK")){
			
		
		
		JSONArray test2= (JSONArray) obj.get("entities");
		int test_len=test2.length();
		List<String> place = new LinkedList<String>();
		List<String> person = new ArrayList<String>();
		List<String> orgs = new ArrayList<String>();
		List<String> twitter_handle = new ArrayList<String>();
		
		for(int i=0;i<test_len;i++){
			JSONObject obj11=(JSONObject) test2.get(i);
			String type=obj11.get("type").toString();
			String text=obj11.get("text").toString();
			
			if(type.equals("Country") || type.equals("City")||type.equals("Continent"))
			{
				place.add(text);
				
			}
			if(type.equals("Person"))
			{
				person.add(text);
			}
			if(type.equals("Organization"))
			{
				orgs.add(text);
			}
			if(type.equals("TwitterHandle"))
			{
				twitter_handle.add(text);
			}
			//System.out.println(type+":"+text);
		}
		if(!(place.isEmpty())){
		String temp_place=place.toString();
		int index_place=temp_place.length()-1;
		j_obj.append("place",temp_place.substring(1,index_place));
		}
		if(!(person.isEmpty())){
		String temp_person=person.toString();
		int index_person=temp_person.length()-1;
		j_obj.append("person",temp_person.substring(1,index_person));
		}
		if(!(orgs.isEmpty())){
		String temp_orgs=orgs.toString();
		int index_orgs=temp_orgs.length()-1;
		j_obj.append("organization",temp_orgs.substring(1,index_orgs));
		}
		if(!(twitter_handle.isEmpty())){
		String temp_twitter_handle=twitter_handle.toString();
		int index_twitter_handle=temp_twitter_handle.length()-1;
		j_obj.append("twitter handle",temp_twitter_handle.substring(1,index_twitter_handle));
		}
		} else{
			API_response+=(String)obj.getString("statusInfo");
		}
		
		return(API_response);
	
	}
	
	

		
		
		
	
}

