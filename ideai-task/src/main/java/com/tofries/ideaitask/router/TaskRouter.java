package com.tofries.ideaitask.router;

import com.tofries.ideai.common.enums.TaskType;
import com.tofries.ideaitask.config.TaskQueueProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TaskRouter {
    private final TaskQueueProperties queueProps;
    
    public String route(TaskType type) {
        return queueProps.getMappings().getOrDefault(type, 
                   queueProps.getDefaultQueue());
    }
}

