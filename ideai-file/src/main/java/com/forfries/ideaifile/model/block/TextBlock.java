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

