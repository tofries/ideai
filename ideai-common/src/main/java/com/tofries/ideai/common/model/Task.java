package com.tofries.ideai.common.model;

import lombok.Data;

@Data
public class Task {
    TaskMeta taskMeta;
    TaskPayload taskPayload;
    TaskResult taskResult;
}
