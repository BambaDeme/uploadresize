package com.example.uploadresize.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    File upload(MultipartFile imageFile);
}