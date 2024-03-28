package com.ssafy.i10a709be.domain.notification.util;

import com.ssafy.i10a709be.domain.notification.service.KafkaNotificationConsumerService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaNotificationUtils {
    private final KafkaAdmin kafkaAdmin;
    private final KafkaNotificationConsumerService kafkaNotificationConsumerService;
    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final KafkaListenerContainerFactory<?> kafkaListenerContainerFactory;

    public void createTopic(String topicName, int numPartitions, short replicationFactor) {
        NewTopic newNotificationTopic = new NewTopic(topicName, numPartitions, replicationFactor);
        AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
        adminClient.createTopics(Collections.singleton(newNotificationTopic));
        adminClient.close();
    }
}
