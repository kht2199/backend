package com.example.backend.notification.controller;

import com.example.backend.notification.dto.NotificationDto;
import com.example.backend.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

@Tag(name = "Notification", description = "알림 API (SSE + Polling)")
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "SSE 구독")
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        return notificationService.subscribe();
    }

    @Operation(summary = "알림 발행")
    @PostMapping("/publish")
    public NotificationDto publish(@RequestBody Map<String, String> body) {
        return notificationService.publish(body.get("message"));
    }

    @Operation(summary = "폴링 조회")
    @GetMapping("/poll")
    public List<NotificationDto> poll(@RequestParam(defaultValue = "0") Long lastId) {
        return notificationService.poll(lastId);
    }
}
