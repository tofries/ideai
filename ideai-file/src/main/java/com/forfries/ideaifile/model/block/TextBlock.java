package com.forfries.ideaifile.model.block;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文本Block
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TextBlock extends Block {
    private String content;
    private TextFormat format;  // 文本格式化信息
}

@Data
public class TextFormat {
    private Boolean bold;
    private Boolean italic;
    private Boolean underline;
    private Boolean strikethrough;
    private String color;
    private String backgroundColor;
} 