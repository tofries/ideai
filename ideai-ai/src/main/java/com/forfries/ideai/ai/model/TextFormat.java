package com.forfries.ideai.ai.model;

import lombok.Data;

@Data
public class TextFormat {
    private boolean bold;
    private boolean italic;
    private boolean underline;
    private boolean strikethrough;
    private String color;
}
