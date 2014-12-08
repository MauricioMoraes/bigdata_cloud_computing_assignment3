package helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;


public class WordcountHelper {

	public static JSONArray getWordcountJsonArray(String filename) throws IOException {
		S3Helper s3Helper = S3Helper.getInstance();
		
		InputStream is = s3Helper.readObject(S3Helper.ASSIGNMENT3_BUCKET_NAME, "web_interface/" + filename);
		
		JSONArray jsonArray = new JSONArray();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			String line;
			while((line = in.readLine()) != null) {
			    StringTokenizer tokenizer = new StringTokenizer(line);
	
			    JSONObject json = new JSONObject();
			    json.put("text", tokenizer.nextToken());
				json.put("weight", Long.valueOf(tokenizer.nextToken()));
			    jsonArray.put(json);
			}
		} 
		catch (NumberFormatException | JSONException e) {
			e.printStackTrace();
		} 
		finally {
			is.close();
		}
		
		return jsonArray;
	}
	
}
