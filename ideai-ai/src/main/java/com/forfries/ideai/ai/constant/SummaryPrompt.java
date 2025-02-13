package com.forfries.ideai.ai.constant;

import io.netty.util.internal.StringUtil;

public class SummaryPrompt implements Prompt{
    private static final String PROMPT = """
            [深度思考模式激活] 你是一个文本总结专家，请你按照用户给出的指令为给出的原始文本进行总结：
            
            **输出约束**
            1. 必须生成纯净的文本类型，不要含有任何MarkDown格式
            2. 输出需要控制在%s字以内
            3. 总结风格：%s
            4. 用户要求：%s
            
            """;

    private static final String USER_MESSAGE= """
            %s
            """;

    private static final Integer DEFAULT_MAX_LENGTH =100;

    private static final String DEFAULT_SUMMARY_STYLE ="简洁";

    private static final String DEFAULT_USER_SUGGESTION = "无额外要求";

    public static String getPrompt(Integer maxLength,String summaryStyle,String userSuggestion) {
        if (maxLength==null||maxLength==0) {
            maxLength = DEFAULT_MAX_LENGTH;
        }
        if (StringUtil.isNullOrEmpty(summaryStyle)) {
            summaryStyle = DEFAULT_SUMMARY_STYLE;
        }
        if (StringUtil.isNullOrEmpty(userSuggestion)) {
            userSuggestion = DEFAULT_USER_SUGGESTION;
        }

        return String.format(PROMPT, maxLength.toString(),summaryStyle,userSuggestion);
    }

    public static String getUserMessage(String originalText) {
        return String.format(USER_MESSAGE,originalText);
    }
}
