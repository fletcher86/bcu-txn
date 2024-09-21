package org.fletcher.model

import groovy.transform.Canonical

import java.time.LocalDate


@Canonical
class BcuTxn {
    private String transactionId
    private LocalDate date
    private String name
    private String description
    private String checkNumber
    private String category
    private BigDecimal amount
    private BigDecimal balance

    // Getters and Setters
}