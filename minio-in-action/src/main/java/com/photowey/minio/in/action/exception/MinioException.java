package com.photowey.minio.in.action.exception;

/**
 * {@code MinioException}
 *
 * @author photowey
 * @date 2022/06/09
 * @since 1.0.0
 */
public class MinioException extends RuntimeException {

    public MinioException() {
        super();
    }

    public MinioException(String message) {
        super(message);
    }

    public MinioException(String message, Throwable cause) {
        super(message, cause);
    }

    public MinioException(Throwable cause) {
        super(cause);
    }

    protected MinioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
