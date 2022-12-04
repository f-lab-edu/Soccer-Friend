package soccerfriend.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import soccerfriend.exception.exception.BadRequestException;

import java.io.*;

import static soccerfriend.exception.ExceptionInfo.FILE_NOT_EXIST;
import static soccerfriend.exception.ExceptionInfo.S3_NOT_WORKING;

@Service
public class S3FileManageService implements FileManageService {

    private final String endPoint;
    private final String regionName;
    private final String accessKey;
    private final String secretKey;
    private final String bucketName;
    private final AmazonS3 s3;

    public S3FileManageService(@Value("${s3.storage.endpoint}") String endPoint,
                               @Value("${s3.storage.regionName}") String regionName,
                               @Value("${s3.storage.accessKey}") String accessKey,
                               @Value("${s3.storage.secretKey}") String secretKey,
                               @Value("${s3.storage.bucketName}") String bucketName) {
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

    /**
     * 파일을 저장소에 업로드합니다.
     *
     * @param file 파일 정보
     */
    @Override
    public void upload(File file) {
        if (file == null) {
            throw new BadRequestException(FILE_NOT_EXIST);
        }

        String objectName = file.getName();

        try {
            s3.putObject(bucketName, objectName, file);
        } catch (SdkClientException e) {
            throw new BadRequestException(S3_NOT_WORKING);
        }
    }

    /**
     * 저장소로부터 파일을 다운로드합니다.
     *
     * @param fileName 파일 이름
     * @param path     파일 경로
     */
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
        } catch (SdkClientException | IOException e) {
            throw new BadRequestException(S3_NOT_WORKING);
        }
    }

    /**
     * 저장소로부터 파일을 삭제합니다.
     *
     * @param fileName 파일 이름
     */
    @Override
    public void delete(String fileName) {
        try {
            s3.deleteObject(bucketName, fileName);
        } catch (SdkClientException e) {
            throw new BadRequestException(S3_NOT_WORKING);
        }
    }


}
