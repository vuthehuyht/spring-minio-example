package com.vuthehuyht.springminioexample.services.impl;

import com.vuthehuyht.springminioexample.services.ImageStoreService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageStorageServiceImpl implements ImageStoreService {
    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @Value("${minio.url}")
    private String minioUrl;

    @Override
    public String uploadImage(MultipartFile file) {
        String fileName = generateFileName(file);

        try(InputStream is = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName).stream(is, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return  "http://localhost:9000/" + bucketName + "/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to store image file.", e);
        }
    }

    private String generateFileName(MultipartFile file) {
        return new Date().getTime() + "-" + Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "_");
    }
}
