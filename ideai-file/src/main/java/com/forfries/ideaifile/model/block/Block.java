package com.forfries.ideaifile.model.block;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Block基类
 */
@Data
@Document(collection = "blocks")
public abstract class Block {
    @Id
    private String id;
    
    private String type;

    private String pageId;

    private String creatorId;   // 创建者ID
    
    private String lastEditorId;  // 最后编辑者ID
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
} 