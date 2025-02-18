package ru.otus.ms.common.utils;

import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

public class DateTimeUtils {

    public static OffsetDateTime localToOffset(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }

        return localDateTime.atZone(ZoneId.systemDefault()).toOffsetDateTime();
    }

    public static LocalDateTime offsetToLocal(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) {
            return null;
        }

        return offsetDateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static long getTimeDiff(LocalDateTime start, LocalDateTime finish, TimeUnit timeUnit) {
        if (ObjectUtils.isEmpty(start) || ObjectUtils.isEmpty(finish)) {
            return 0;
        }

        if (start.isAfter(finish)) {
            return 0;
        }

        long diffInMillis = Duration.between(start, finish).toMillis();
        return timeUnit.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }
}
