package com.forfries.ideai.ai.service;

import com.tofries.ideai.common.common.ErrorCode;
import com.tofries.ideai.common.constant.RedisTaskConstant;
import com.tofries.ideai.common.enums.TaskStatus;
import com.tofries.ideai.common.enums.TaskType;
import com.tofries.ideai.common.exception.BusinessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ReplayProcessor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class StreamService {
    private final ConcurrentMap<TaskType, ConcurrentMap<String, ReplayProcessor<String>>> cachedProcessors = new ConcurrentHashMap<>();
    private final RedisTemplate<String, Object> redisTemplate;

    public StreamService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 添加并开始订阅流
     *
     * @param taskId 任务ID
     * @param taskType 任务类型
     * @param flux 数据流
     */
    public void addStream(String taskId, TaskType taskType, Flux<String> flux) {
        // 创建 ReplayProcessor 来缓存和重播流
        ReplayProcessor<String> processor = ReplayProcessor.create();
        StringBuilder stringBuilder = new StringBuilder();
        // 订阅原始流并将数据发送到 ReplayProcessor
        flux.doOnNext(stringBuilder::append)
            .doOnComplete(() -> {
                redisTemplate.opsForValue().set(
                        RedisTaskConstant.getResultKey(taskId,taskType),
                        stringBuilder.toString()
                );

                redisTemplate.opsForValue().set(
                    RedisTaskConstant.getStatusKey(taskId, taskType),
                    TaskStatus.SUCCESS
                );
        }).doOnError(e -> {
            redisTemplate.opsForValue().set(
                    RedisTaskConstant.getStatusKey(taskId, taskType),
                    TaskStatus.FAILED
            );
        }).subscribe(processor);

        // 确保任务类型存在
        cachedProcessors.computeIfAbsent(taskType, k -> new ConcurrentHashMap<>());

        // 存储 ReplayProcessor
        cachedProcessors.get(taskType).put(taskId, processor);
    }

    /**
     * 获取指定任务类型的流
     *
     * @param taskId 任务ID
     * @param taskType 任务类型
     * @return 缓存的流
     */
    public Flux<String> getStream(String taskId, TaskType taskType) {
        ConcurrentMap<String, ReplayProcessor<String>> stringProcessorConcurrentMap = cachedProcessors.get(taskType);
        if (stringProcessorConcurrentMap == null || !stringProcessorConcurrentMap.containsKey(taskId)) {
            return Flux.error(new BusinessException(ErrorCode.NOT_FOUND_ERROR, "无该Task,有可能过期"));
        }
        return stringProcessorConcurrentMap.get(taskId).asFlux();
    }
}