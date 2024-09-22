package org.fletcher.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info

@Configuration
@EnableWebMvc
class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BCU Transaction API")
                        .version("1.0")
                        .description("API documentation for BCU Transaction Service"))
    }

    @Override
    void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springdoc-openapi-ui/")
                .resourceChain(false)
    }
}