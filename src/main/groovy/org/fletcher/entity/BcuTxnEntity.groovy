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
    public String transactionId
    public String accountId
    public LocalDate date
    public String name
    public String description
    public String checkNumber
    public String category
    public BigDecimal amount
    public BigDecimal balance

}