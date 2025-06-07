package org.fletcher.model

import com.opencsv.bean.CsvBindByName
import com.opencsv.bean.CsvCustomBindByName
import com.opencsv.bean.CsvDate
import org.fletcher.utils.AccountIDConverter
import org.fletcher.utils.BigDecimalConverter
import org.fletcher.utils.LocalDateConverter
import java.math.BigDecimal
import java.time.LocalDate

data class BcuTxn(
    @CsvCustomBindByName(column = "Account ID", converter = AccountIDConverter::class)
    val accountId: String? = null,

    @CsvBindByName(column = "Transaction ID")
    val transactionId: String? = null,

    @CsvCustomBindByName(column = "Date", converter = LocalDateConverter::class)
    @CsvDate("MM/dd/yy")
    val date: LocalDate? = null,

    @CsvBindByName(column = "Name")
    val name: String? = null,

    @CsvBindByName(column = "Description")
    val description: String? = null,

    @CsvBindByName(column = "Check Number")
    val checkNumber: String? = null,

    @CsvBindByName(column = "Category")
    val category: String? = null,

    @CsvBindByName(column = "Tags")
    val tags: String? = null,

    @CsvCustomBindByName(column = "Amount", converter = BigDecimalConverter::class)
    val amount: BigDecimal? = null,

    @CsvCustomBindByName(column = "Balance", converter = BigDecimalConverter::class)
    val balance: BigDecimal? = null
)