package org.fletcher.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "transactions")
data class BcuTxnEntity(
    @Id
    var transactionId: String? = "",
    var accountId: String? = null,
    var date: LocalDate? = null,
    var name: String? = null,
    var description: String? = null,
    var checkNumber: String? = null,
    var category: String? = null,
    var amount: BigDecimal? = null,
    var balance: BigDecimal? = null
)