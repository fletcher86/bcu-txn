package org.fletcher.config

import groovy.util.logging.Slf4j
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
@ComponentScan(basePackages = ["org.fletcher"])
@Slf4j
class Config {

    @Bean(name = "restTemplate")
    RestTemplate restTemplate() {
        return new RestTemplate()
    }
}
