package org.fletcher.utils

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvConstraintViolationException
import com.opencsv.exceptions.CsvDataTypeMismatchException
import java.math.BigDecimal

class BigDecimalConverter : AbstractBeanField<BigDecimal, String>() {
    @Throws(CsvDataTypeMismatchException::class, CsvConstraintViolationException::class)
    override fun convert(value: String?): BigDecimal? {
        if (value.isNullOrEmpty()) {
            return null
        }
        // Remove any currency symbols and commas
        val sanitizedValue = value.replace(Regex("[\$,]"), "")
        return BigDecimal(sanitizedValue)
    }
}