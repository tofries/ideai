package com.forfries.ideaifile.model.block;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 列表Block
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ListBlock extends Block {
    private String content;
    private Boolean checked;  // 仅用于待办事项列表
    private Integer indent;   // 缩进级别
} 