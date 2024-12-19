package com.forfries.ideaifile.model.block;

import lombok.Data;

@Data
public class TextFormat {
    private Boolean bold;
    private Boolean italic;
    private Boolean underline;
    private Boolean strikethrough;
    private String color;
}
