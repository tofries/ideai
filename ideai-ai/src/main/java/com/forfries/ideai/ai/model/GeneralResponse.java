package com.forfries.ideai.ai.model;

import lombok.Data;

@Data
public class GeneralResponse<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> GeneralResponse<T> success(T data) {
        GeneralResponse<T> response = new GeneralResponse<>();
        response.setCode(0);
        response.setMessage("success");
        response.setData(data);
        return response;
    }

    public static <T> GeneralResponse<T> error(String message) {
        GeneralResponse<T> response = new GeneralResponse<>();
        response.setCode(-1);
        response.setMessage(message);
        return response;
    }
}
