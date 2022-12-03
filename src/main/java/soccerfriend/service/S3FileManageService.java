package soccerfriend.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Value;
import soccerfriend.exception.exception.BadRequestException;

import java.io.*;

import static soccerfriend.exception.ExceptionInfo.FILE_NOT_EXIST;

public class S3FileManageService implements FileManageService {

    private final String endPoint;
    private final String regionName;
    private final String accessKey;
    private final String secretKey;
    private final String bucketName;
    private final AmazonS3 s3;

    public S3FileManageService(@Value("${ncp.storage.endpoint}") String endPoint,
                               @Value("${ncp.storage.regionName}") String regionName,
                               @Value("${ncp.storage.accessKey}") String accessKey,
                               @Value("${ncp.storage.secretKey}") String secretKey,
                               @Value("${ncp.storage.bucketName}") String bucketName) {
        this.endPoint = endPoint;
        this.regionName = regionName;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketName = bucketName;

        // S3 client
        s3 = AmazonS3ClientBuilder.standard()
                                  .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
                                  .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                                  .build();
    }

    @Override
    public void upload(File file) {
        if (file == null) {
            throw new BadRequestException(FILE_NOT_EXIST);
        }

        String objectName = file.getName();

        try {
            s3.putObject(bucketName, objectName, file);
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void download(String fileName, String path) {
        try {
            S3Object s3Object = s3.getObject(bucketName, fileName);
            S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();

            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));
            byte[] bytesArray = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = s3ObjectInputStream.read(bytesArray)) != -1) {
                outputStream.write(bytesArray, 0, bytesRead);
            }

            outputStream.close();
            s3ObjectInputStream.close();
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
