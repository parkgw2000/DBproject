package com.example.dbproject.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
@Slf4j
@Service
public class FileService {
    @Value("${file.upload.path}")
    private String uploadPath;

    public byte[] convertToByte(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            try {
                return file.getBytes();
            } catch (IOException e) {
                log.error("File conversion failed: ", e);
                throw e;
            }
        }
        return null;
    }

    // String 경로를 byte[]로 변환
    public byte[] convertToByte(String filePath) throws IOException {
        if (filePath != null && !filePath.isEmpty()) {
            return filePath.getBytes();
        }
        return null;
    }

    // byte[]를 다시 String으로 변환
    public String convertToString(byte[] bytes) {
        if (bytes != null) {
            return new String(bytes);
        }
        return null;
    }

    public String saveFile(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                return Base64.getEncoder().encodeToString(bytes);
            } catch (IOException e) {
                log.error("File save failed: ", e);
                throw e;
            }
        }
        return null;
    }
}