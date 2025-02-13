package com.tofries.ideai.common.constant;

import com.tofries.ideai.common.enums.TaskType;

public class RedisTaskConstant {
    public static final String PAYLOAD_KEY = "tasks:%s:payload:%s";
    public static final String STATUS_KEY = "tasks:%s:status:%s";
    public static final String RESULT_KEY = "tasks:%s:result:%s";

    public static String getPayloadKey(String taskId, TaskType taskType) {
        return java.lang.String.format(PAYLOAD_KEY, taskType, taskId);
    }

    public static String getStatusKey(String taskId, TaskType taskType) {
        return String.format(STATUS_KEY, taskType, taskId);
    }
    public static String getResultKey(String taskId, TaskType taskType) {
        return String.format(RESULT_KEY, taskType, taskId);
    }
}
