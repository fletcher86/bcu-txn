package org.fletcher

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableTransactionManagement
@SpringBootApplication
class Application {
    static void main(String[] args) {
        SpringApplication.run(Application, args)
    }
}
