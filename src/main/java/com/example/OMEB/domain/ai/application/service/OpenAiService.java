package com.example.OMEB.domain.ai.application.service;

import com.example.OMEB.domain.ai.presentation.dto.response.AiTagResponse;
import com.example.OMEB.domain.review.persistence.vo.TagName;
import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.base.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:security/application-security.yml")
public class OpenAiService {
    @Value("${spring.ai.openai.api-key}")
    private String apiKey;
    @Value("${spring.ai.openai.chat.options.model}")
    private String model;

    public AiTagResponse chatToAi(String text){
        StringBuilder sb = new StringBuilder();
        sb.append("다음 텍스트의 감정을 분류하세요 ")
                .append("설명하지 말고 무조건 하나의 단어로만 대답해야합니다. : ")
                .append(text).append("\n")
                .append("감정 : DEPRESSION, ANGER, ANXIETY, LONELINESS, JEALOUSY, HAPPINESS, " +
                        "LETHARGY, LOVE, ACCOMPLISHMENT");
        ChatResponse response = new OpenAiChatModel(new OpenAiApi(apiKey))
                .call(new Prompt(sb.toString(),
                        OpenAiChatOptions.builder()
                                .withModel(model).build()));
        String tag = response.getResult().getOutput().getContent().toUpperCase();
        try {
            TagName.valueOf(tag);
        } catch (IllegalArgumentException e) {
            sb.append("\n유효하지 않은 감정 태그입니다: ").append(tag)
                    .append("\n제발 아무 설명도 하지 말고 위의 감정 중 하나의 단어로만 대답해");

            response = new OpenAiChatModel(new OpenAiApi(apiKey))
                    .call(new Prompt(sb.toString(),
                            OpenAiChatOptions.builder()
                                    .withModel(model).build()));
            tag = response.getResult().getOutput().getContent().toUpperCase();
            try {
                TagName.valueOf(tag);
            } catch (IllegalArgumentException ex) {
                throw new ServiceException(ErrorCode.NOT_FOUND_TAG);
            }
        }
        return new AiTagResponse(tag);
    }
}
