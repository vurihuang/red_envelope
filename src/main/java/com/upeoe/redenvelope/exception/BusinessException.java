package com.upeoe.redenvelope.exception;

/**
 * @author upeoe
 * @create 2019/4/11 00:58
 */
public class BusinessException extends Exception {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

}
