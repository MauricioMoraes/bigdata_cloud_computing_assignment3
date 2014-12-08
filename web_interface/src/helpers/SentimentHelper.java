package helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import com.amazonaws.util.json.JSONArray;


public class SentimentHelper {

	public static JSONArray getSentimentDataArray() throws IOException {
		S3Helper s3Helper = S3Helper.getInstance();
		
		InputStream is = s3Helper.readObject(S3Helper.ASSIGNMENT3_BUCKET_NAME, "web_interface/sentiment_timeseries");
		
		JSONArray jsonArray = new JSONArray();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			String line;
			while((line = in.readLine()) != null) {
			    StringTokenizer tokenizer = new StringTokenizer(line);
			    jsonArray.put(Double.valueOf(tokenizer.nextToken()));
			}
		} 
		catch (NumberFormatException e) {
			e.printStackTrace();
		} 
		finally {
			is.close();
		}
		
		return jsonArray;
	}
	
}
