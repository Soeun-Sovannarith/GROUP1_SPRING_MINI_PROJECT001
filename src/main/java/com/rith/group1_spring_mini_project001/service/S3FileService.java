package com.rith.group1_spring_mini_project001.service;
import com.rith.group1_spring_mini_project001.model.model.FileMetadata;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface S3FileService {
    FileMetadata uploadFile(MultipartFile file);

    Resource getFileByFileName(String fileName);
}
