package com.esteban.framework.exception;

/**
 * 登陆异常<br>
 *
 * @author esteban
 * @since 2014年5月23日
 */
public class AdminLoginException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 构造方法
     */
    public AdminLoginException() {
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
    public AdminLoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * 构造方法
     *
     * @param message
     * @param cause
     */
    public AdminLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造方法
     *
     * @param message
     */
    public AdminLoginException(String message) {
        super(message);
    }

    /**
     * 构造方法
     *
     * @param cause
     */
    public AdminLoginException(Throwable cause) {
        super(cause);
    }

}
