package com.etlions.ms_cv_identity_doc.infrastructure.openfeign.clients;

import com.etlions.ms_cv_identity_doc.infrastructure.openfeign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@FeignClient(
        name = "personImageClient",
        url = "https://thispersondoesnotexist.com",
        configuration = FeignConfig.class
)
public interface PersonImageClient {
    @GetMapping(value = "/", produces = MediaType.IMAGE_JPEG_VALUE)
    byte[] getRandomPersonImage();
}