package com.rith.group1_spring_mini_project001.controller;

import com.rith.group1_spring_mini_project001.model.model.FileMetadata;
import com.rith.group1_spring_mini_project001.model.response.ApiResponse;
import com.rith.group1_spring_mini_project001.service.S3FileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@Tag(name = "file-controller")
public class S3FileController {
    private final S3FileService s3FileService;
    @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<FileMetadata>> uploadFile(@RequestParam MultipartFile file) {

        FileMetadata fileMetadata = s3FileService.uploadFile(file);

        ApiResponse<FileMetadata> response = ApiResponse.<FileMetadata>builder()
                .success(true)
                .status(HttpStatus.CREATED)
                .message("File uploaded successfully to RustFS")
                .data(fileMetadata)
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/preview-file/{file-name}")
    public ResponseEntity<Resource> getFileByFileName(@PathVariable("file-name") String fileName) {
        Resource resource  = s3FileService.getFileByFileName(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }
}
