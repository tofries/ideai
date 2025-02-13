package com.forfries.ideai.ai.strategy;

import com.forfries.ideai.ai.enums.AIStrategyType;
import com.forfries.ideai.ai.factory.AIStrategyFactory;
import com.forfries.ideai.ai.model.response.StreamAIResponse;
import com.tofries.ideai.common.model.SummaryTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SummaryAIStrategyTest {
    @Autowired
    private AIStrategyFactory aiStrategyFactory;

    @Test
    void summary() {
        SummaryAIStrategy aiStrategy = (SummaryAIStrategy) aiStrategyFactory.getAIStrategy(AIStrategyType.SUMMARY);

        StreamAIResponse output = aiStrategy.execute(SummaryTask.builder()
                .originalText("""
                        全球气候变化已成为21世纪最严峻的环境挑战。根据联合国政府间气候变化专门委员会（IPCC）第六次评估报告，过去50年地球表面温度上升速度是近2000年来最快的时期，北极海冰面积每十年减少约13%，极端天气事件频率增加300%以上。
                        
                        造成这种现象的主因是人类活动产生的温室气体排放。工业革命以来，大气中二氧化碳浓度已从280ppm激增至420ppm，其中75%来自化石燃料燃烧，20%源自森林砍伐。值得注意的是，尽管2020年全球疫情导致经济活动放缓，二氧化碳排放量仅暂时下降5.6%，2021年立即反弹至疫情前水平。
                        
                        农业部门的影响常被低估：畜牧业贡献了14.5%的全球温室气体排放，水稻种植产生的甲烷占农业排放量的30%。与此同时，海洋吸收了约30%人为排放的二氧化碳，导致海水酸化速度比自然过程快100倍，已威胁到珊瑚礁和贝类生物。
                        
                        解决方案需要多维度协同：1）能源转型方面，太阳能光伏成本十年间下降82%，风电下降39%，但可再生能源仅占当前全球能源结构的17% 2）碳捕捉技术如BECCS（生物能源与碳捕获封存）理论上可实现负排放，但大规模应用仍需突破成本瓶颈 3）基于自然的解决方案，恢复2.5亿公顷退化土地可吸收相当于全球年排放量1/3的二氧化碳。
                        
                        国际气候协定执行面临挑战，《巴黎协定》要求的1.5℃温控目标需要全球在2030年前减少45%碳排放，但目前各国承诺仅达成7.5%减排量。发展中国家要求的气候资金到位率不足40%，技术转让机制尚未有效建立。
                        """).build());

        output.getStream().doOnNext(System.out::print).blockLast();
    }
}