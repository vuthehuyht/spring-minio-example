package com.vuthehuyht.springminioexample.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStoreService {
    String uploadImage(MultipartFile file);
}
