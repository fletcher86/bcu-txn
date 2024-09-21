package org.fletcher

import groovy.util.logging.Slf4j
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@Slf4j
@SpringBootApplication
class BcuTxnApp {

    static void main(String[] args) {
        SpringApplication.run(BcuTxnApp, args)
    }
}