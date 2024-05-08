package com.ocunha.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Value("${api.result.size:5}")
    private Integer apiResultSize;

    public Integer getApiResultSize() {
        return apiResultSize;
    }
}
