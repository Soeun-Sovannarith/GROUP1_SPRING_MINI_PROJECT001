package com.rith.group1_spring_mini_project001.service.impl;

import com.rith.group1_spring_mini_project001.exception.InvalidFileException;
import com.rith.group1_spring_mini_project001.exception.ResourceNotFoundException;
import com.rith.group1_spring_mini_project001.model.model.FileMetadata;
import com.rith.group1_spring_mini_project001.service.S3FileService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class S3FileServiceImpl implements S3FileService {
    private final S3Client s3Client;
    @Value("${rustfs.bucket.name}")
    private String bucketName;

    public void createBucketIfNotExists() {
        try {
            s3Client.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());
        } catch (NoSuchBucketException e) {
            s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
        }
    }

    @SneakyThrows
    @Override
    public FileMetadata uploadFile(MultipartFile file) {
        createBucketIfNotExists();

        String originalFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(originalFileName);
        String contentType = file.getContentType();
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v2/files/preview-file/" + fileName)
                .toUriString();

        if (!fileUrl.matches(".*\\.(png|svg|jpg|jpeg|gif)$")) {
            throw new InvalidFileException("Profile image must be a valid image URL ending with .png, .svg, .jpg, .jpeg, or .gif");
        }
        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .contentType(contentType)
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to RustFS", e);
        }

        return FileMetadata.builder()
                .fileName(fileName)
                .fileType(file.getContentType())
                .fileUrl(fileUrl)
                .fileSize(file.getSize())
                .build();
    }
    @SneakyThrows
    @Override
    public Resource getFileByFileName(String fileName) {
        try {
            InputStream inputStream = s3Client.getObject(
                    GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .build()
            );
            // Wrap the InputStream in a Spring Resource and return it to controller
            return new InputStreamResource(inputStream);

        } catch (S3Exception e) {
            if (e.statusCode() == 404) {
                throw new ResourceNotFoundException("File not found: " + fileName);
            }
            throw new RuntimeException("S3 Service Error", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to download file from RustFS", e);
        }
    }
}
