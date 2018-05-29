package com.esteban.core.framework.exception;

/**
 * 登陆异常<br>
 *
 * @author esteban
 * @since 2014年5月23日
 */
public class NotLoginException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 构造方法
     */
    public NotLoginException() {
        super();
    }

    /**
     * 构造方法
     *
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public NotLoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * 构造方法
     *
     * @param message
     * @param cause
     */
    public NotLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造方法
     *
     * @param message
     */
    public NotLoginException(String message) {
        super(message);
    }

    /**
     * 构造方法
     *
     * @param cause
     */
    public NotLoginException(Throwable cause) {
        super(cause);
    }

}
