package com.tofries.ideai.common.model;

import com.tofries.ideai.common.enums.TaskType;
import lombok.Data;

@Data
public class SummaryTask implements TaskPayload {
    private String originalText;
    private String userSuggestion;
    private Integer maxLength;
    
    @Override
    public TaskType getType() {
        return TaskType.SUMMARY;
    }
}