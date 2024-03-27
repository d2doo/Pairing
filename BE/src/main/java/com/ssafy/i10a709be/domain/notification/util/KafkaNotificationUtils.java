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
        NewTopic newChatTopic = new NewTopic(topicName, numPartitions, replicationFactor);
        AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
        adminClient.createTopics(Collections.singleton(newChatTopic));
        adminClient.close();
    }

    public void createAndRegisterListener(String topicName, String groupId) {
        // KafkaListenerEndpoint 인스턴스 생성
        MethodKafkaListenerEndpoint<String, String> endpoint = new MethodKafkaListenerEndpoint<>();
        endpoint.setId(groupId + "-" + topicName); // 고유 ID 설정
        endpoint.setGroupId(groupId); // 그룹 ID 설정
        endpoint.setTopics(topicName); // 리스너가 구독할 토픽 설정
        endpoint.setMessageHandlerMethodFactory(new DefaultMessageHandlerMethodFactory());
        endpoint.setBean(kafkaNotificationConsumerService); // 메시지를 처리할 빈 설정
        try {
            endpoint.setMethod(kafkaNotificationConsumerService.getClass().getMethod("consume", String.class)); // 처리 메소드 설정
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Method not found", e);
        }
        kafkaListenerEndpointRegistry.registerListenerContainer(endpoint, kafkaListenerContainerFactory, true);
    }
}
