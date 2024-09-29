package org.fletcher.entity

import groovy.transform.Canonical
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

import java.time.LocalDate

@Entity
@Table(name = "transactions")
@Canonical
class BcuTxnEntity {
    @Id
    private String transactionId
    private String accountId
    private LocalDate date
    private String name
    private String description
    private String checkNumber
    private String category
    private BigDecimal amount
    private BigDecimal balance

}