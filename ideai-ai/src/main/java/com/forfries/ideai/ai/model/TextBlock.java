package com.forfries.ideai.ai.model;

import lombok.Data;

@Data
public class TextBlock {
    private String id;
    private String type;
    private String pageId;
    private String creatorId;
    private String lastEditorId;
    private String createTime;
    private String updateTime;
    private String content;
    private TextFormat format;
}
