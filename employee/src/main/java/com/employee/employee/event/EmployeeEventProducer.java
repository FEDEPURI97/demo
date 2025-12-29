package com.employee.employee.event;

import com.employee.employee.dto.UserRegisteredDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Log4j2
public class EmployeeEventProducer {

    private final KafkaSender<String, UserRegisteredDto> kafkaSender;

    public Mono<Void> sendUserRegisteredEvent(UserRegisteredDto event) {
        ProducerRecord<String, UserRegisteredDto> producer = new ProducerRecord<>("user-registered", event.id().toString(), event);
        SenderRecord<String, UserRegisteredDto, UUID> senderRecord = SenderRecord.create(producer, UUID.randomUUID());
        return kafkaSender.send(Mono.just(senderRecord))
                .doOnError(e -> log.error("Errore invio evento: {}", e.getMessage()))
                .doOnNext(r -> log.info("Messaggio inviato correttamente: {}", r.correlationMetadata()))
                .then();
    }

}
