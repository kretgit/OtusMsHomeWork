package ru.otus.ms.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> handleException(CommonException ex) {
        if (log.isDebugEnabled()) {
            log.error(ex.getMessage(), ex);
        } else {
            log.error(ex.getMessage());
        }

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
