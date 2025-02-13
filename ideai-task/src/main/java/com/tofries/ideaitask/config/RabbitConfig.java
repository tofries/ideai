package com.tofries.ideaitask.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RabbitConfig {

    @Bean
    public Declarables taskQueues(TaskQueueProperties props) {
        List<Declarable> declarables = new ArrayList<>();

        props.getMappings().forEach((type, queueName) -> {
            declarables.add(new Queue(queueName, true));
            declarables.add(new DirectExchange(queueName + ".exchange"));
            declarables.add(BindingBuilder
                    .bind(new Queue(queueName))
                    .to(new DirectExchange(queueName + ".exchange"))
                    .with(queueName));
        });

        return new Declarables(declarables);
    }
}
