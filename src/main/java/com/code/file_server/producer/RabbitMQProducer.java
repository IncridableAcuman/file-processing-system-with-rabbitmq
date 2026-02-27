package com.code.file_server.producer;

import com.code.file_server.config.RabbitMQConfig;
import com.code.file_server.entity.FileEntity;
import com.code.file_server.enums.Status;
import com.code.file_server.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQProducer {
    private final FileRepository fileRepository;


    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void processFile(Long fileId){
        FileEntity fileEntity = fileRepository.findById(fileId).orElseThrow(()->new RuntimeException("File not found"));

        try {
            fileEntity.setStatus(Status.PROCESSING);
            fileRepository.save(fileEntity);
            Thread.sleep(5000);

            fileEntity.setStatus(Status.DONE);
            fileRepository.save(fileEntity);
        } catch (Exception e) {
            fileEntity.setStatus(Status.FAILED);
            fileRepository.save(fileEntity);
        }
    }
}
