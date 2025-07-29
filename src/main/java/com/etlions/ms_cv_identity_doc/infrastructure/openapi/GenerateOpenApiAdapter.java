package com.etlions.ms_cv_identity_doc.infrastructure.openapi;

import com.etlions.openapicodegen.api.v1.GenerateApi;
import com.etlions.openapicodegen.dto.GenerateDNIRequest;
import com.etlions.openapicodegen.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Controller
public class GenerateOpenApiAdapter implements GenerateApi {

    @Override
    public ResponseEntity<SuccessResponse> generateDniDocuments(GenerateDNIRequest generateDNIRequest) {
        return ResponseEntity.ok(SuccessResponse.builder()
                .isSuccess(true)
                .message("Endpoint working")
                .data(generateDNIRequest)
                .timestamp(LocalDateTime.now())
                .build());
    }
}
