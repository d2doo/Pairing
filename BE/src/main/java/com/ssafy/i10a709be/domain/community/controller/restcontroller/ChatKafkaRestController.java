package com.ssafy.i10a709be.domain.community.controller.restcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka/chat")
public class ChatKafkaRestController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    // KafkaTemplate을 주입받는 생성자
    public ChatKafkaRestController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

//    // Kafka에 메시지를 보내는 엔드포인트
//    @PostMapping("/chat-room/{roomId}")
//    public ResponseEntity<String> sendMessage(@PathVariable("roomId") Long roomId, @RequestParam("message") String message) {
//        // Kafka 토픽과 메시지를 지정하여 전송
//        kafkaTemplate.send("roomId", message);
//        return ResponseEntity.ok("Message sent to Kafka topic successfully!");
//    }
//
//    // Kafka로부터 메시지를 받는 엔드포인트
//    @KafkaListener(topics = "topic_name", groupId = "group_id")
//    public void consumeMessage(String message) {
//        // 받은 메시지를 로그에 출력 또는 처리
//        System.out.println("Received message from Kafka: " + message);
//        // 여기서 메시지를 원하는 대로 처리할 수 있습니다.
//    }
}