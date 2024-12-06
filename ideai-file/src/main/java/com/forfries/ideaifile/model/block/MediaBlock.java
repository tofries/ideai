package com.forfries.ideaifile.model.block;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 媒体Block（图片、视频、文件等）
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MediaBlock extends Block {
    private String url;
    private String caption;
    private String mimeType;
    private Long size;
    private String originalName;
} 