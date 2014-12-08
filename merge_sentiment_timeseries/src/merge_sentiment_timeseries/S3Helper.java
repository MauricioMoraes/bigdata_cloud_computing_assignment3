package merge_sentiment_timeseries;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Region;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3Helper
{
	private static S3Helper sS3Helper;
	public static S3Helper getInstance() throws IOException {
		if (sS3Helper == null) {
			sS3Helper = new S3Helper(new DefaultAWSCredentialsProviderChain().getCredentials());
		}
		return sS3Helper;
	}

	public static final String ASSIGNMENT3_BUCKET_NAME = "jhm-assignment3";
	private AmazonS3Client mS3Client = null;	
	
	public S3Helper(AWSCredentials awsCredentials) {
		try {
			// so amazon refreshes the credentials automatically
			this.mS3Client = new AmazonS3Client(new InstanceProfileCredentialsProvider());
			// test if the credentials work
			mS3Client.listBuckets();
		}
		catch (AmazonClientException e) {
			// probably is not in an EC2 instance, then look for the credentials in the default chain
			// (credentials will expire)
			this.mS3Client = new AmazonS3Client(new DefaultAWSCredentialsProviderChain().getCredentials());
		}

	}
	
	public void createBucketIfNeeded(String bucketName) {
		 try {
			 if(!mS3Client.doesBucketExist(bucketName)) {
				 // create bucket
				 mS3Client.createBucket(bucketName, Region.US_Standard);
	         }
         } 
		 catch (Exception e) {
			 e.printStackTrace();
         }
	}
	
	public InputStream readObject(String bucketName, String key) {
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
		S3Object object = mS3Client.getObject(getObjectRequest);
		return object.getObjectContent();
	}
	
	public void uploadObject(String bucketName, String key, File file) {
		mS3Client.putObject(new PutObjectRequest(bucketName, key, file));
	}
	
	public void copyObject(String sourceBucketName, String sourceKey, String destinationBucketName, String destinationKey) {
		mS3Client.copyObject(sourceBucketName, sourceKey, destinationBucketName, destinationKey);
	}
	
	public List<String> listObjectsInFolder(String bucketName, String folderName) {
		ListObjectsRequest lor = new ListObjectsRequest().withBucketName(bucketName).withPrefix(folderName);
		List<S3ObjectSummary> summaries = mS3Client.listObjects(lor).getObjectSummaries();
		ArrayList<String> keys = new ArrayList();
		
		for (S3ObjectSummary summary : summaries) {
			keys.add(summary.getKey());
		}
		
		return keys;
	}
}