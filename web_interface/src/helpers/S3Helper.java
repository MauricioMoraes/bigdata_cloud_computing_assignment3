package helpers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Region;
import com.amazonaws.services.s3.model.S3Object;

public class S3Helper
{
	private static S3Helper sS3Helper;
	public static S3Helper getInstance() throws IOException {
		if (sS3Helper == null) {
			sS3Helper = new S3Helper(ApplicationHelper.getCredentials());
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

		// set region to US East
		mS3Client.setRegion(ApplicationHelper.getAmazonRegion());
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
	
//	public Video storeIntoBucket(String key, InputStream is)
//	{
//		System.out.println("Trying to store object into S3 bucket ...");
//		Video video = null;
//		try
//		{
//			if (VideoStore.bucketName.equals(""))
//			{
//				throw new AmazonServiceException("Bucket name can't be empty.");
//			}
//			
//			if (this.s3 == null)
//			{
//				throw new AmazonServiceException("AmazonS3Client object can't be null.");
//			}
//			
//			AccessControlList acl = new AccessControlList();
//			acl.grantPermission(GroupGrantee.AllUsers, Permission.FullControl);
//    		
//			s3.putObject(new PutObjectRequest(VideoStore.bucketName, key, is, null).withAccessControlList(acl));
////			s3.putObject(this.bucketName, key, is, null);
//			System.out.println("Upload to S3 done.");
//			    		
//    		System.out.println("Collecting  details ...");
//    		S3Object s3Object = s3.getObject(new GetObjectRequest(bucketName, key));
//    		String bucketName = s3Object.getBucketName();
//    		String videoName = s3Object.getKey();
//    		String eTag = s3Object.getObjectMetadata().getETag();
////    		String videoLink = s3Object.getObjectContent().getHttpRequest().getURI().toString();
//    		String videoFormat = "default";
//    		
//    		OnDemandDistributor distributor = new OnDemandDistributor().withAWSCredentials(new BasicAWSCredentials(AwsCredentialConstants.ACCESS.getValue(), AwsCredentialConstants.SECRET.getValue()));
//    		String webDistributionDomainName = distributor.getWebDistributionName();
//    		String videoLink = "http://" + webDistributionDomainName + "/" + videoName;
//    		
//    		video = new Video()
//    						.withBucketName(bucketName)
//    						.withVideoName(videoName)
//    						.withETag(eTag)
//    						.withVideoLink(videoLink)
//    						.withVideoFormat(videoFormat)
//    						.withDefaultRating();
//    		System.out.println("Details collected: " + video.toString());
//	    }
//		catch (Exception e)
//	    {
//	        e.printStackTrace();
//	   	}
//		return video;
//	}
}