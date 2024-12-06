package com.forfries.ideaifile.model.block;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 标题Block
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HeadingBlock extends Block {
    private String content;
    private Integer level;  // 标题级别：1-3
} 