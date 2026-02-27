package com.code.file_server.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
public class FileUtil {
    @Value("${file.upload.dir}")
    private String uploadDir;

//    public String saveFile(MultipartFile multipartFile){
//        String originalName = multipartFile.getOriginalFilename();
//        String filename = UUID.randomUUID() + "." + originalName;
//    }
}
