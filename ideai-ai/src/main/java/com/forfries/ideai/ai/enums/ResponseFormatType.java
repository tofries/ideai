package com.forfries.ideai.ai.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseFormatType {
    TEXT("text"),
    JSON("json_object");

    private final String type;
}
