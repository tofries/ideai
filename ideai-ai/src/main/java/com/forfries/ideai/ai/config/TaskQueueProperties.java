package com.forfries.ideai.ai.config;


import com.tofries.ideai.common.enums.TaskType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "task.queue")
public class TaskQueueProperties {
    // Getter/Setter
    /**
     * 默认任务队列名称
     */
    private String defaultQueue = "ai.default_tasks";

    /**
     * 任务类型与队列的映射关系
     */
    private Map<TaskType, String> mappings = new HashMap<>();

}