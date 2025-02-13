package com.tofries.ideai.common.model;

import com.tofries.ideai.common.enums.TaskStatus;
import com.tofries.ideai.common.enums.TaskType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TaskMeta {

    private String taskId = UUID.randomUUID().toString();

    private TaskType type;

    private TaskStatus status = TaskStatus.PENDING;
//
//    private LocalDateTime createdAt = LocalDateTime.now();
//
}
