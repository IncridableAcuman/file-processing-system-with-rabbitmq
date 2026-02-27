package com.code.file_server.service;

import com.code.file_server.config.RabbitMQConfig;
import com.code.file_server.dto.FileRequest;
import com.code.file_server.dto.FileResponse;
import com.code.file_server.entity.FileEntity;
import com.code.file_server.enums.Status;
import com.code.file_server.repository.FileRepository;
import com.code.file_server.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final RabbitTemplate rabbitTemplate;
    private final FileUtil fileUtil;

    public FileResponse saveAndSendToQueue(FileRequest request) throws IOException {
        String path = fileUtil.saveFile(request.getFile());

        FileEntity fileEntity = FileEntity
                .builder()
                .filePath(path)
                .filename(request.getFile().getOriginalFilename())
                .status(Status.PROCESSING)
                .time(LocalDateTime.now())
                .build();

        FileEntity saved = fileRepository.save(fileEntity);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.QUEUE_EXCHANGE,
                RabbitMQConfig.QUEUE_KEY,
                saved.getId()
        );
        return new FileResponse(
                fileEntity.getId(),
                fileEntity.getFilename(),
                fileEntity.getFilePath(),
                fileEntity.getStatus(),
                saved.getTime()
        );
    }
}
