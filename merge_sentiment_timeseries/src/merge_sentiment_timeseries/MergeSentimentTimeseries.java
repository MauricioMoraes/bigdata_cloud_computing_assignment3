package merge_sentiment_timeseries;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MergeSentimentTimeseries {
	
	public static void main(String[] args) throws IOException {
		S3Helper s3Helper = S3Helper.getInstance();
		
		s3Helper.createBucketIfNeeded(S3Helper.ASSIGNMENT3_BUCKET_NAME);
		
		// get all file names
		List<String> keys = s3Helper.listObjectsInFolder(S3Helper.ASSIGNMENT3_BUCKET_NAME, "sentiment_output");
		ArrayList<BufferedReader> ins = new ArrayList<BufferedReader>(keys.size());
		
		for (String key : keys) {
			InputStream is = s3Helper.readObject(S3Helper.ASSIGNMENT3_BUCKET_NAME, key);
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			ins.add(in);
		}
		
		File file = new File("sentiment_timeseries");
		PrintWriter writer = new PrintWriter(file);
		
		int index = 0;
		while (true) {
			if (ins.isEmpty())
				break;
			
			index = index % ins.size();
			
			BufferedReader in = ins.get(index);
			String line = in.readLine();
			if (line != null) {
			    StringTokenizer tokenizer = new StringTokenizer(line);
			    tokenizer.nextToken();
			    writer.println(tokenizer.nextToken());
			    index += 1;
			}
			else {
				in.close();
				ins.remove(index);
			}
		}
		writer.close();
		s3Helper.uploadObject(S3Helper.ASSIGNMENT3_BUCKET_NAME, "web_interface/sentiment_timeseries", file);
	}

}
