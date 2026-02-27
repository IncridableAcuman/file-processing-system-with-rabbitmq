package com.code.file_server.controller;

import com.code.file_server.dto.FileRequest;
import com.code.file_server.dto.FileResponse;
import com.code.file_server.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> saveAndSendToQueue(@ModelAttribute FileRequest request) throws IOException {
        return ResponseEntity.ok(fileService.saveAndSendToQueue(request));
    }
}
