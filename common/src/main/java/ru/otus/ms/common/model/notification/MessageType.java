package ru.otus.ms.common.model.notification;

import lombok.Getter;

@Getter
public enum MessageType {
    SYSTEM_INFO,
    SYSTEM_ERROR,
    NEW_USER,
    NEW_POST
}
