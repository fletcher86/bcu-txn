package org.fletcher.model

import com.opencsv.bean.CsvBindByName
import com.opencsv.bean.CsvCustomBindByName
import com.opencsv.bean.CsvDate
import groovy.transform.Canonical
import org.fletcher.utils.AccountIDConverter
import org.fletcher.utils.BigDecimalConverter
import org.fletcher.utils.LocalDateConverter

import java.time.LocalDate

@Canonical
class BcuTxn {

    @CsvCustomBindByName(column = "Account ID", converter = AccountIDConverter)
    private String accountId

    @CsvBindByName(column = "Transaction ID")
    private String transactionId

    @CsvCustomBindByName(column = "Date", converter = LocalDateConverter)
    @CsvDate("MM/dd/yy")
    private LocalDate date

    @CsvBindByName(column = "Name")
    private String name

    @CsvBindByName(column = "Description")
    private String description

    @CsvBindByName(column = "Check Number")
    private String checkNumber

    @CsvBindByName(column = "Category")
    private String category

    @CsvBindByName(column = "Tags")
    private String tags

    @CsvCustomBindByName(column = "Amount", converter = BigDecimalConverter)
    private BigDecimal amount

    @CsvCustomBindByName(column = "Balance", converter = BigDecimalConverter)
    private BigDecimal balance

}