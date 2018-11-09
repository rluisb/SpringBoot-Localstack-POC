package br.com.ilegra.file.watcher;

import br.com.ilegra.file.watcher.config.AwsS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

@SpringBootApplication
public class WatcherApplication {
    public static void main(String[] args) {
        SpringApplication.run(WatcherApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(AwsS3Client awsS3Client) {
        return args -> {

            File file = File.createTempFile("aws-java-sdk-", ".txt");
            file.deleteOnExit();

            Writer writer = new OutputStreamWriter(new FileOutputStream(file));
            writer.write("abcdefghijklmnopqrstuvwxyz\n");
            writer.write("01234567890112345678901234\n");
            writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
            writer.write("01234567890112345678901234\n");
            writer.write("abcdefghijklmnopqrstuvwxyz\n");
            writer.close();

            System.out.println("Uploading a new object to " + awsS3Client.getAwsS3Bucket() + " from a file\n");
            awsS3Client.amazonS3().putObject(new PutObjectRequest(awsS3Client.getAwsS3Bucket(), file.getName(), file));

            System.out.println("Listing objects");
            awsS3Client.listAllObjects()
                    .forEach(s3ObjectSummary -> {
                        System.out.println("Key ==> " + s3ObjectSummary.getKey());
                        System.out.println("Bucket Name ==> " + s3ObjectSummary.getBucketName());
                        System.out.println("Size ==> " + s3ObjectSummary.getSize());
                    });

            System.out.println("Finding objects by key");
            S3ObjectSummary s3ObjectSummary = awsS3Client.getObjectByKey(file.getName());
            System.out.println("Key ==> " + s3ObjectSummary.getKey());
            System.out.println("Bucket Name ==> " + s3ObjectSummary.getBucketName());
            System.out.println("Size ==> " + s3ObjectSummary.getSize());
        };
    }


}
