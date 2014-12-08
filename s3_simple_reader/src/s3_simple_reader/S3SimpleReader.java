package s3_simple_reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class S3SimpleReader {

	private static final int LINES_TO_READ = 400;
	
	public static void main(String[] args) throws IOException {
		S3Helper s3Helper = S3Helper.getInstance();

//		InputStream is = s3Helper.readObject("ColumbiaCloud", "final/Assignment3Tweets-2");
//		InputStream is = s3Helper.readObject("jhm-assignment3", "trender_output/part-r-00001");
		InputStream is = s3Helper.readObject("jhm-assignment3", "sentiment_output/part-r-00000");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF8"));
		String line;
		for (int i=0; i<LINES_TO_READ; i++) {
			line = in.readLine();
			if (line == null)
				break;
			
			System.out.println(line);
		}
		is.close();
		
		long a = 123L;
		double b = (double) (Double.valueOf(a)/a);
	}

}
