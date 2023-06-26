package com.bingo.appbingo.application.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan("com.bingo.appbingo.infrastructure.entry_points.api.*")
@ComponentScan(basePackages = "com.bingo.appbingo.domain.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class ConfigApp {


}
