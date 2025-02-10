package com.forfries.ideai.ai.model;

import lombok.Data;
import java.util.List;

@Data
public class CompletionResponse {
    private String pageId;
    private List<TextBlock> blocks;

    @Data
    public static class TextBlock {
        private String blockId;
        private List<Error> errors;
        private String correctedText;
    }

    @Data
    public static class Error {
        private String errorType;
        private TextRange textRange;
        private String originalText;
        private String suggestion;
        private String message;
    }

    @Data
    public static class TextRange {
        private Integer start;
        private Integer end;
    }
}
