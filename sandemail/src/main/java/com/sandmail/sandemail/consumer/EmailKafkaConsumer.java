package com.sandmail.sandemail.consumer;

import com.sandmail.sandemail.dto.UserRegisteredDto;
import com.sandmail.sandemail.service.EmailService;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailKafkaConsumer {

    private final ReactiveKafkaConsumerTemplate<String, UserRegisteredDto> kafkaConsumer;
    private final EmailService emailService;
    @Value("${spring.kafka.consumer.properties.spring.json.trusted.packages}")
    private String trustedPackages;
    @Value("${spring.kafka.bootstrap-servers}")
    private String server;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;
    @Value("${app.activation.register-topic}")
    private String kafkaTopic;

    public EmailKafkaConsumer(EmailService emailService) {
        this.emailService = emailService;
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, trustedPackages);

        ReceiverOptions<String, UserRegisteredDto> receiverOptions =
                ReceiverOptions.<String, UserRegisteredDto>create(props)
                        .subscription(Collections.singleton(kafkaTopic));

        this.kafkaConsumer = new ReactiveKafkaConsumerTemplate<>(receiverOptions);
    }

    @PostConstruct
    public void consume() {
        kafkaConsumer.receiveAutoAck()
                .map(ConsumerRecord::value)
                .doOnNext(emailService::sendEmail)
                .subscribe();
    }

}
