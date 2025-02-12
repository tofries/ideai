package com.tofries.ideai.common.model;



import com.tofries.ideai.common.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Task {
    private String taskId;
    private TaskStatus status;
    private String inputText;
    private String summary;
    private LocalDateTime createdAt;
}