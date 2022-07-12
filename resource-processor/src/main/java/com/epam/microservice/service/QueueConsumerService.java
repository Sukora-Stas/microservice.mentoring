package com.epam.microservice.service;

import com.epam.microservice.client.ResourceServiceClient;
import com.epam.microservice.client.SongServiceClient;
import com.epam.microservice.model.SongMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QueueConsumerService {

    private final RabbitTemplate rabbitTemplate;
    private final ResourceServiceClient resourceServiceClient;
    private final ResourceMetadataProcessorService resourceProcessorService;
    private final SongServiceClient songServiceClient;

    @Value("${spring.rabbitmq.dead-letter-max-deaths-count}")
    private Integer maxDeathsCount;

    @Value("${spring.rabbitmq.parking-lot-exchange-name}")
    private String resourceCreatedEventParkingLotExchangeName;

    @RabbitListener(queues = "${spring.rabbitmq.queue-name}")
    public void receiveMessage(Long resourceId, Message message) {
        if (shouldProcess(message.getMessageProperties().getHeader("x-death"))) {
            byte[] resourceBinaryData =
                    resourceServiceClient.getResourceBinaryData(resourceId);
            SongMetadata songMetadata =
                    resourceProcessorService.retrieveResourceMetadata(resourceBinaryData, resourceId);
            songServiceClient.saveSong(songMetadata);
        } else {
            sendMessageToParkingLotQueue(message);
        }
    }

    private void sendMessageToParkingLotQueue(Message message) {
        rabbitTemplate.setExchange(resourceCreatedEventParkingLotExchangeName);
        rabbitTemplate.send(message);
    }

    private boolean shouldProcess(List<Map<String, Object>> queueHeaders) {
        if (queueHeaders != null) {
            Long count = (Long) queueHeaders.stream().map(m -> m.get("count")).findFirst().orElse(0L);
            return count <= maxDeathsCount;
        }
        return true;
    }
}
