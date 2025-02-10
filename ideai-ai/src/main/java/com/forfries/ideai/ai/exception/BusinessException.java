package com.forfries.ideai.ai.exception;


public class BusinessException extends RuntimeException {
    // 常规构造函数
    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public static void throwIf(boolean condition, String message) {
        if (condition) {
            throw new BusinessException(message);
        }
    }
}
