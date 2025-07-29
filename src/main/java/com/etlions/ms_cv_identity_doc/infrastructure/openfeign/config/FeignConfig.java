package com.etlions.ms_cv_identity_doc.infrastructure.openfeign.config;

import com.etlions.ms_cv_identity_doc.core.exception.ImageFetchException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(1000, 2000, 3); // Retry: 1s, 2s, 3 intentos
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) ->
                new ImageFetchException("Error al obtener imagen: " + response.status());
    }
}