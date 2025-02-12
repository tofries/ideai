package com.forfries.ideai.ai.strategy;

import com.forfries.ideai.ai.enums.AIStrategyType;
import com.forfries.ideai.ai.factory.AIStrategyFactory;
import com.forfries.ideai.ai.model.input.CSV2EchartsTask;
import com.forfries.ideai.ai.model.output.CSV2EchartsOutput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CSVAnalyzeAIStrategyTest {
    @Autowired
    AIStrategyFactory aiStrategyFactory;

    @Test
    void analyze() {
        CSVAnalyzeAIStrategy strategy = (CSVAnalyzeAIStrategy) aiStrategyFactory.getAIStrategy(AIStrategyType.CSV_ANALYZE);
        CSV2EchartsOutput output = strategy.execute(CSV2EchartsTask.builder()
                .csvData("月份,销售额,成本 1月,10000,4000 2月,15000,5000")
                .target("显示季度趋势并分析成本结构")
                .build());
        output.getStream().doOnNext(System.out::print).blockLast();

    }

}