package com.code.file_server.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class FileUtil {
    @Value("${file.upload.dir}")
    private String uploadDir;

    public String saveFile(MultipartFile multipartFile) throws IOException {

        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        String originalName = multipartFile.getOriginalFilename();
        String extension="";

        if (originalName != null && originalName.contains(".")){
            extension=originalName.substring(originalName.lastIndexOf("."));
        }
        String uniqueFilename=UUID.randomUUID()  + extension;

        Path filePath = uploadPath.resolve(uniqueFilename);

        Files.copy(multipartFile.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);

        return filePath.toString();
    }
}
