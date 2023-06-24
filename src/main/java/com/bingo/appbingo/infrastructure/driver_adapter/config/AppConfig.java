package com.bingo.appbingo.infrastructure.driver_adapter.config;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper reactiveCommonsObjectMapper() {
        return new ObjectMapper() {
            @Override
            public <T> T map(Object src, Class<T> target) {
                return null;
            }

            @Override
            public <T> T mapBuilder(Object src, Class<T> target) {
                return null;
            }
        };
    }
}
