package com.code.file_server.dto;

import com.code.file_server.enums.Status;

import java.time.LocalDateTime;

public record FileResponse(
        Long id,
        String filename,
        String filePath,
        Status status,
        LocalDateTime createdAt
) {
}
