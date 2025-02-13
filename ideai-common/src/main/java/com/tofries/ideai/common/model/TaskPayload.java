package com.tofries.ideai.common.model;

import com.tofries.ideai.common.enums.TaskType;
import lombok.Data;

// 存储在Redis的Hash结构
public interface TaskPayload {
    TaskType getType();
}




