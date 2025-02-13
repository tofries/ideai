package com.forfries.ideai.ai.listener;

import cn.hutool.core.map.MapUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forfries.ideai.ai.config.TaskQueueProperties;
import com.forfries.ideai.ai.enums.AIStrategyType;
import com.forfries.ideai.ai.factory.AIStrategyFactory;
import com.forfries.ideai.ai.model.response.StreamAIResponse;
import com.forfries.ideai.ai.service.StreamService;
import com.forfries.ideai.ai.strategy.AbstractAIStrategy;
import com.forfries.ideai.ai.strategy.SummaryAIStrategy;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQChannel;
import com.rabbitmq.client.impl.AMQImpl;
import com.tofries.ideai.common.common.ErrorCode;
import com.tofries.ideai.common.constant.RedisTaskConstant;
import com.tofries.ideai.common.enums.TaskStatus;
import com.tofries.ideai.common.enums.TaskType;
import com.tofries.ideai.common.exception.BusinessException;
import com.tofries.ideai.common.model.SummaryTask;
import com.tofries.ideai.common.model.TaskPayload;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.BindException;

@Component
@AllArgsConstructor
@Slf4j
public class SummaryListener {

    private final TaskQueueProperties properties;
    private final RedisTemplate<String,Object> redisTemplate;
    private final AIStrategyFactory strategyFactory;
    private final StreamService streamService;

    @RabbitListener(queues = "${task.queue.mappings.SUMMARY}")
    public void handleMessage(@Payload String message,
                              @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                              Channel channel) throws IOException {

        if(StringUtil.isNullOrEmpty(message)) {
            channel.basicNack(deliveryTag, false,false);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"消息为空");
        }
        SummaryTask payload = (SummaryTask) redisTemplate.opsForValue().get(RedisTaskConstant.getPayloadKey(message, TaskType.SUMMARY));

        if(payload == null) {
            channel.basicNack(deliveryTag, false,false);
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"数据不存在");
        }
        String statusKey = RedisTaskConstant.getStatusKey(message, TaskType.SUMMARY);
        if(StringUtil.isNullOrEmpty(statusKey)) {
            channel.basicNack(deliveryTag, false,false);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"状态无法找到");
        }
        log.info("正在处理消息：{}",message);
        redisTemplate.opsForValue().set(
                statusKey,
                TaskStatus.PROCESSING);

        try {
            SummaryAIStrategy summaryAIStrategy = (SummaryAIStrategy) strategyFactory.getAIStrategy(AIStrategyType.SUMMARY);
            StreamAIResponse output = summaryAIStrategy.execute(payload);

            streamService.addStream(message,TaskType.SUMMARY,output.getStream());

            channel.basicAck(deliveryTag, false);

        } catch (Exception e) {
            redisTemplate.opsForValue().set(statusKey, TaskStatus.FAILED);
            channel.basicNack(deliveryTag, false, false); // 重新入队throw new BusinessException(ErrorCode.SYSTEM_ERROR,e.getMessage());
        }
    }
}
