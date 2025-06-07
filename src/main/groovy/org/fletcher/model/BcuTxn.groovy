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
    public String accountId

    @CsvBindByName(column = "Transaction ID")
    public String transactionId

    @CsvCustomBindByName(column = "Date", converter = LocalDateConverter)
    @CsvDate("MM/dd/yy")
    public LocalDate date

    @CsvBindByName(column = "Name")
    public String name

    @CsvBindByName(column = "Description")
    public String description

    @CsvBindByName(column = "Check Number")
    public String checkNumber

    @CsvBindByName(column = "Category")
    public String category

    @CsvBindByName(column = "Tags")
    public String tags

    @CsvCustomBindByName(column = "Amount", converter = BigDecimalConverter)
    public BigDecimal amount

    @CsvCustomBindByName(column = "Balance", converter = BigDecimalConverter)
    public BigDecimal balance

}