package com.forfries.ideai.ai.controller;

import com.forfries.ideai.ai.service.StreamService;
import com.tofries.ideai.common.common.ErrorCode;
import com.tofries.ideai.common.constant.RedisTaskConstant;
import com.tofries.ideai.common.enums.TaskStatus;
import com.tofries.ideai.common.enums.TaskType;
import com.tofries.ideai.common.exception.BusinessException;
import com.tofries.ideai.common.utils.ThrowUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Objects;

@RestController
@RequestMapping("/ai/task")
public class TaskController {

    private final RedisTemplate redisTemplate;

    private final StreamService streamService;

    public TaskController(RedisTemplate redisTemplate, StreamService streamService) {
        this.redisTemplate = redisTemplate;
        this.streamService = streamService;
    }

    @GetMapping(value = "/summary/test",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> test() {
        return Flux.just("1","2","3");
    }

    @GetMapping("/{taskType}/status/{taskId}")
    public TaskStatus getTaskStatus(
            @PathVariable("taskType") String taskTypeString,
            @PathVariable("taskId") String taskId) {

        TaskType taskType;
        try {
            taskType = TaskType.valueOf(taskTypeString.toUpperCase());
            return TaskStatus.valueOf( Objects.requireNonNull(redisTemplate.opsForValue().get(
                    RedisTaskConstant.getStatusKey(taskId, taskType)
            )).toString());
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "访问地址错误！" + e.getMessage());
        }

    }

    @GetMapping(value = "/{taskType}/{taskId}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getTaskStream(
            @PathVariable("taskType") String taskTypeString,
            @PathVariable("taskId") String taskId) {
        TaskType taskType;
        try{
             taskType = TaskType.valueOf(taskTypeString.toUpperCase());
        }catch (Exception e){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "访问地址错误！");
        }
        TaskStatus taskStatus = this.getTaskStatus(taskTypeString, taskId);
        Flux<String> ret;

        switch (taskStatus){
            case PROCESSING:
                ret = streamService.getStream(taskId, taskType);
                break;
            case SUCCESS:
                Object o = redisTemplate.opsForValue().get(
                        RedisTaskConstant.getResultKey(taskId, taskType)
                );
                ThrowUtils.throwIf(o==null,ErrorCode.SYSTEM_ERROR,"没有找到结果");
                ret = Flux.just(o.toString());
                break;
            default:
                ret = Flux.empty();
                break;
        }
        return ret;
    }
}
