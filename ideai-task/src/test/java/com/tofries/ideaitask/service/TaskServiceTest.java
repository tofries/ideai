package com.tofries.ideaitask.service;

import com.tofries.ideai.common.enums.TaskType;
import com.tofries.ideai.common.model.SummaryTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceTest {
    @Autowired
    private TaskService taskService;
    @Test
    void createTask() {
        taskService.createTask(TaskType.SUMMARY,new SummaryTask());
    }

    @Test
    void createAndGetStatus() {
        String task = taskService.createTask(TaskType.SUMMARY, new SummaryTask());
        System.out.println(taskService.getTaskStatus(task));
    }
}