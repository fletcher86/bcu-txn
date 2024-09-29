package org.fletcher.config

import jakarta.annotation.PostConstruct
import org.apache.commons.beanutils.ConvertUtils
import org.fletcher.utils.LocalDateConverter
import org.springframework.context.annotation.Configuration

import java.time.LocalDate

@Configuration
class ConverterConfig {

    @PostConstruct
    void init() {
//        ConvertUtils.register(new LocalDateConverter("MM/dd/yy"), LocalDate.class)
    }
}