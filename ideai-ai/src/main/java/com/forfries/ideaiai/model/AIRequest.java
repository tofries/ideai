package com.forfries.ideaiai.model;

import lombok.Data;
import java.util.List;

@Data
public class AIRequest {
    private String preset;
    private List<TextBlock> blocks;
}
