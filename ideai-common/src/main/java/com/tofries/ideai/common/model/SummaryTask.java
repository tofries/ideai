package com.tofries.ideai.common.model;

import com.tofries.ideai.common.enums.TaskType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryTask implements TaskPayload {
    private String summaryStyle;
    private String originalText;
    private String userSuggestion;
    private Integer maxLength;


    @Override
    public TaskType type() {
        return TaskType.SUMMARY;
    }
}