package com.emgram.kr.dobby.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

    @Bean
    public ObjectMapper holidayApiObjectMapper() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(dateFormat);
        return objectMapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(dateFormat);
        return objectMapper;
    }
}
