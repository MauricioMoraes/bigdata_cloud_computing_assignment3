package find_top_trends;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class FindTopTrends {

	static final String[] BLACKLIST = new String[]{"https", "est","del","ni","yg","ser","dia","esta","voc", "vc","ti","ur","que","se","un","lo","con","para","si","yo","im","sos","como","aku","http","il","ba","xx","ki","ke", "nos", "va", "uma", "meu", "ko","mo","hi","qu","ka","ah","et","ha","ini","ada","itu","pra","su","ta","em","les","um","os","oh","je","sa","ma","di","mas","pero","una","eu","na","up","ya","mi","da","do","le", "la","les","las","por", "co","rt","el","en","de","te","es","los","tu","al","vou","voy"}; 
	
	public static void main(String[] args) throws IOException {
		S3Helper s3Helper = S3Helper.getInstance();
		
		s3Helper.createBucketIfNeeded(S3Helper.ASSIGNMENT3_BUCKET_NAME);
		
		// get all file names
		List<String> keys = s3Helper.listObjectsInFolder(S3Helper.ASSIGNMENT3_BUCKET_NAME, "trender_output");
		
		// setup blacklist
		HashSet<String> blacklist = new HashSet<String>();
		for (String str : BLACKLIST) {
			blacklist.add(str);
		}
		
		// read word count
		int topCount = 200;
		PriorityQueue<WordcountPair> heap = new PriorityQueue<WordcountPair>(topCount);
		
		for (String key : keys) {
			InputStream is = s3Helper.readObject(S3Helper.ASSIGNMENT3_BUCKET_NAME, key);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			String line;
			while((line = in.readLine()) != null) {
			    StringTokenizer tokenizer = new StringTokenizer(line);
			    String word = tokenizer.nextToken();
			    
			    if (blacklist.contains(word))
			    	continue;
			    
			    long count = Long.valueOf(tokenizer.nextToken());
			    
			    if (heap.size() < topCount || heap.peek().mCount < count) {
			    	if (heap.size() == topCount)
			    		heap.poll();
			    	heap.add(new WordcountPair(word, count));
			    }
			}
			is.close();
			
		}
		File file = new File("top_trends");
		PrintWriter writer = new PrintWriter(file);
		
		WordcountPair pair;
		while ((pair = heap.poll()) != null) {
			System.out.println("word: " + pair.mWord + "    count: " + String.valueOf(pair.mCount));
			writer.println(pair.mWord + "\t" + String.valueOf(pair.mCount));
		}
		writer.close();
		
		s3Helper.uploadObject(S3Helper.ASSIGNMENT3_BUCKET_NAME, "web_interface/top_trends", file);
	}

}
