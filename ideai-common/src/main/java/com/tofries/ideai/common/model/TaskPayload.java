package com.tofries.ideai.common.model;

import com.tofries.ideai.common.enums.TaskType;

// 存储在Redis的Hash结构
public interface TaskPayload {
    TaskType type();
}




