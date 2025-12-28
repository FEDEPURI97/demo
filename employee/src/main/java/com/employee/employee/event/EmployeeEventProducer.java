package com.employee.employee.event;

import com.employee.employee.dto.UserRegisteredDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EmployeeEventProducer {

    private final KafkaSender<String, UserRegisteredDto> kafkaSender;

    public Mono<Void> sendUserRegisteredEvent(UserRegisteredDto event) {
        ProducerRecord<String, UserRegisteredDto> message =
                new ProducerRecord<>("user-registered", event.id().toString(), event);
        SenderRecord<String, UserRegisteredDto, UUID> senderRecord = SenderRecord.create(message, UUID.randomUUID());
        return kafkaSender.send(Mono.just(senderRecord))
                .then();
    }

}
