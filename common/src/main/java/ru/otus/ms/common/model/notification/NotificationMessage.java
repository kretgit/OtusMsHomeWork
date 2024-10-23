package ru.otus.ms.common.model.notification;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class NotificationMessage {

    public NotificationMessage(MessageType messageType, String message) {
        this.messageType = messageType;
        this.message = message;
    }

    private MessageType messageType;
    private String message;
    private OffsetDateTime created = OffsetDateTime.now();
}
