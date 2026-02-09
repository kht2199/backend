package com.example.backend.notification.service;

import com.example.backend.notification.dto.NotificationDto;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class NotificationService {

    private static final long SSE_TIMEOUT = 60_000L;

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private final List<NotificationDto> store = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(SSE_TIMEOUT);

        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(e -> emitters.remove(emitter));

        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("connected"));
        } catch (IOException e) {
            emitters.remove(emitter);
        }

        return emitter;
    }

    public NotificationDto publish(String message) {
        NotificationDto notification = NotificationDto.builder()
                .id(idGenerator.incrementAndGet())
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();

        store.add(notification);

        emitters.removeIf(emitter -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(notification));
                return false;
            } catch (IOException e) {
                return true;
            }
        });

        return notification;
    }

    public List<NotificationDto> poll(Long lastId) {
        return store.stream()
                .filter(n -> n.getId() > lastId)
                .toList();
    }
}
