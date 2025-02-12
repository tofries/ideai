package com.forfries.ideai.ai.model.input;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CSV2EchartsTask {
    private String id;
    private String csvData;
    private String target;
    private String echartsStyle;
}
