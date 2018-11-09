package br.com.ilegra.file.watcher.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AwsS3Client {

    @Value("${aws.s3.endpoint}")
    private String awsS3Endpoint;
    @Value("${aws.s3.region}")
    private String awsS3Region;
    @Value("${aws.s3.bucket}")
    private String awsS3Bucket;

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new ProfileCredentialsProvider().getCredentials()))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(awsS3Endpoint, awsS3Region))
                .build();
    }

    public List<S3ObjectSummary> listAllObjects() {
        return amazonS3().listObjects(new ListObjectsRequest()
                .withBucketName(awsS3Bucket))
                .getObjectSummaries();
    }

    public S3ObjectSummary getObjectByKey(String key) {
        return amazonS3().listObjects(new ListObjectsRequest()
                .withBucketName(awsS3Bucket))
                .getObjectSummaries()
                .stream()
                .filter(s3ObjectSummary -> s3ObjectSummary.getKey().equals(key))
                .findFirst()
                .get();
    }

    public String getAwsS3Bucket() {
        return awsS3Bucket;
    }
}
