package com.photowey.netty.telnet.in.action.exception;

/**
 * {@code NamedException}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
public class NamedException extends RuntimeException {

    public NamedException() {
    }

    public NamedException(String message) {
        super(message);
    }

    public NamedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NamedException(Throwable cause) {
        super(cause);
    }

    public NamedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
