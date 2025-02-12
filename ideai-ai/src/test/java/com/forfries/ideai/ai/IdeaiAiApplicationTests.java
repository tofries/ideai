package com.forfries.ideai.ai;

import com.forfries.ideai.ai.enums.ResponseFormatType;
import com.forfries.ideai.ai.model.request.ResponseFormat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IdeaiAiApplicationTests {

    @Test
    void contextLoads() {
        ResponseFormatType format = ResponseFormatType.JSON;
        System.out.println(format.getType());
    }

}
