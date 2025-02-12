package com.forfries.ideai.ai.model.request;

import com.forfries.ideai.ai.enums.ResponseFormatType;
import lombok.Getter;

@Getter
public class ResponseFormat {
    private String type;

    public ResponseFormat(ResponseFormatType type) {
        if(type == null) {
            this.type = "text";
            return;
        }
        this.type = type.getType();
    }
}
