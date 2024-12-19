package com.forfries.ideaifile.dto;

import lombok.Data;

/**
 * Block操作的消息DTO
 */
@Data
public class BlockOperationDTO {
    private String operationType;  // 操作类型：CREATE, UPDATE, DELETE
    private String blockId;        // 被操作的blockId
    private String pageId;         // 所属页面ID
    private String userId;         // 操作用户ID
    private Object blockData;      // block的数据
    private Long timestamp;        // 操作时间戳
    private Integer version;       // 版本号，用于处理并发冲突
} 