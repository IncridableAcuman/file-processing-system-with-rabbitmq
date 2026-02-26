package com.code.file_server.dto;

import com.code.file_server.enums.Status;

import java.time.LocalDateTime;

public record FileResponse(
        String id,
        String filename,
        String filePath,
        Status status,
        LocalDateTime time
) {
}
