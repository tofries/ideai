package com.tofries.ideaitask.service;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tofries.ideai.common.common.ErrorCode;
import com.tofries.ideai.common.constant.RedisTaskConstant;
import com.tofries.ideai.common.enums.TaskStatus;
import com.tofries.ideai.common.enums.TaskType;
import com.tofries.ideai.common.exception.BusinessException;
import com.tofries.ideai.common.model.*;
import com.tofries.ideaitask.constant.TaskDuration;
import com.tofries.ideaitask.router.TaskRouter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Arrays;

import static com.tofries.ideai.common.utils.ThrowUtils.throwIf;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final TaskRouter taskRouter;
    private final RabbitTemplate rabbitTemplate;

    @Transactional(rollbackFor = Exception.class)
    public String createTask(TaskType type, TaskPayload payload) {
        // 1. 保存元数据
        TaskMeta meta = new TaskMeta();
        meta.setType(type);
        String statusRedisKey = String.format(RedisTaskConstant.STATUS_KEY,
                type.name(),
                meta.getTaskId());
        String payloadRedisKey = String.format(RedisTaskConstant.PAYLOAD_KEY,
                type.name(),
                meta.getTaskId());
        try {
            //保存元数据 可以持久化到数据库中，这里简单化处理
            //2.存储status信息到Redis中
            redisTemplate.opsForValue().set(
                    statusRedisKey,
                    meta.getStatus(),
                    Duration.ofMinutes(TaskDuration.DEFAULT_TASK_MIN));

            // 3. 存储payload到Redis
            redisTemplate.opsForValue().set(
                    payloadRedisKey,
                    payload,
                    Duration.ofMinutes(TaskDuration.DEFAULT_TASK_MIN)
            );


            // 4. 发送到消息队列
            rabbitTemplate.convertAndSend(
                    taskRouter.route(type),
                    meta.getTaskId()
            );

            return meta.getTaskId();
        } catch (Exception e) {
            // 补偿机制：删除已创建的元数据
            redisTemplate.opsForValue().getAndDelete(statusRedisKey);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
    }

    public TaskStatus getTaskStatus(String taskId,TaskType taskType) {
        String status = (String) redisTemplate.opsForValue().get(RedisTaskConstant.getStatusKey(taskId, taskType));
        throwIf(status == null|| status.isEmpty(),ErrorCode.PARAMS_ERROR,"请求的Task不存在，可能过期或错误");
        return TaskStatus.valueOf(status);
    }

    public TaskResult getTaskResult(String taskId,TaskType taskType) {
        TaskResult taskResult = (TaskResult) redisTemplate.opsForValue().get(RedisTaskConstant.getResultKey(taskId, taskType));
        return taskResult;
    }

}
