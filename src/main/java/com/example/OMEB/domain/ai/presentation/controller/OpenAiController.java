package com.example.OMEB.domain.ai.presentation.controller;

import com.example.OMEB.domain.ai.application.service.OpenAiService;
import com.example.OMEB.domain.ai.presentation.api.OpenAiControllerApi;
import com.example.OMEB.domain.ai.presentation.dto.request.UserTextRequest;
import com.example.OMEB.domain.ai.presentation.dto.response.AiTagResponse;
import com.example.OMEB.global.base.dto.ResponseBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.OMEB.global.base.dto.SuccessResponseBody.createSuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ai")
public class OpenAiController implements OpenAiControllerApi {
    private final OpenAiService openAiService;

    @PostMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseBody<AiTagResponse>> convertToTag(@RequestBody @Valid UserTextRequest userTextRequest){
        return ResponseEntity.ok(createSuccessResponse(openAiService.chatToAi(userTextRequest.getText())));
    }

}
