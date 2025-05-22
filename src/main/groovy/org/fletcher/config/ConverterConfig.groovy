package org.fletcher.config

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration

@Configuration
class ConverterConfig {

    @PostConstruct
    void init() {
//        ConvertUtils.register(new LocalDateConverter("MM/dd/yy"), LocalDate.class)
    }
}