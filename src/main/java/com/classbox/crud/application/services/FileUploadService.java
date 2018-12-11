package com.classbox.crud.application.services;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    
    void uploadFile(MultipartFile file) throws IOException;
    
    byte[] getFileAsByteArray(String fileName) throws IOException;
}
