package copy_tweet_data;

import java.io.IOException;


public class CopyTweetData {

	public static void main(String[] args) throws IOException {
		S3Helper s3Helper = S3Helper.getInstance();
		s3Helper.createBucketIfNeeded(S3Helper.ASSIGNMENT3_BUCKET_NAME);
		
		s3Helper.copyObject(
			"ColumbiaCloud", 
			"final/Assignment3Tweets-2", 
			S3Helper.ASSIGNMENT3_BUCKET_NAME, 
			"tweet_data"
		);
	}

}
