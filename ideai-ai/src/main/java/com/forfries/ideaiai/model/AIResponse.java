package com.forfries.ideaiai.model;

import lombok.Data;

@Data
public class AIResponse<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> AIResponse<T> success(T data) {
        AIResponse<T> response = new AIResponse<>();
        response.setCode(0);
        response.setMessage("success");
        response.setData(data);
        return response;
    }

    public static <T> AIResponse<T> error(String message) {
        AIResponse<T> response = new AIResponse<>();
        response.setCode(-1);
        response.setMessage(message);
        return response;
    }
}
