package ru.otus.ms.common.exception;

public class CommonException extends RuntimeException {
    public CommonException() {

    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }


}
