package com.classbox.crud.application.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service(value = "SimpleFileUploadService")
public class SimpleFileUploadService implements FileUploadService {

    private String uploadDirectory = "C:\\imagenes\\";

    public void setUploadDirectory(String uploadDirectory) {
        this.uploadDirectory = uploadDirectory;
    }
    
    public String getUploadDirectory() {
        return uploadDirectory;
    }
    
    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        initUploadDirectory();
        String originalFileName = file.getOriginalFilename();
        File destination = new File(uploadDirectory + originalFileName);
        file.transferTo(destination);
    }
    
    @Override
    public byte[] getFileAsByteArray(String fileName) throws IOException {
        File file = new File(uploadDirectory + fileName);
        return Files.readAllBytes(file.toPath());
    }
    
    private void initUploadDirectory() throws IOException {
        if (!new File(uploadDirectory).exists()) {
            new File(uploadDirectory).mkdir();
        }
    }
}
