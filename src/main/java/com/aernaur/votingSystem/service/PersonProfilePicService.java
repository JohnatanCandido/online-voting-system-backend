package com.aernaur.votingSystem.service;

import com.aernaur.votingSystem.entity.Person;
import com.aernaur.votingSystem.exceptions.ProfilePicUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonProfilePicService {

    @Value("${aws.bucket.name}")
    private String awsBucketName;

    private final S3Client s3Client;

    public String uploadProfilePic(UUID personId, MultipartFile multipartFile) throws ProfilePicUploadException {
        try {
            File file = convertMultipartToFile(personId, multipartFile);
            s3Client.putObject(PutObjectRequest.builder()
                                               .bucket(awsBucketName)
                                               .key(file.getName())
                                               .build(),
                    RequestBody.fromFile(file));
            Files.delete(file.toPath());
            return file.getName();
        } catch (Exception e) {
            throw new ProfilePicUploadException();
        }
    }

    private File convertMultipartToFile(UUID personId, MultipartFile multipartFile) throws IOException {
        File convFile = new File(personId.toString() + "-" + multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }

    public String getProfilePicUrl(Person person) {
        return getProfilePicUrl(person.getProfilePicS3Name());
    }

    public String getProfilePicUrl(String profilePicS3Name) {
        return s3Client.utilities()
                       .getUrl(GetUrlRequest.builder().bucket(awsBucketName).key(profilePicS3Name).build())
                       .toString();
    }

    public void deleteProfilePic(Person person) {
        s3Client.deleteObject(DeleteObjectRequest.builder().bucket(awsBucketName).key(person.getProfilePicS3Name()).build());
    }
}
