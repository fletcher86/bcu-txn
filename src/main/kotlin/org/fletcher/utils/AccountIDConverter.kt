package org.fletcher.utils

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvConstraintViolationException
import com.opencsv.exceptions.CsvDataTypeMismatchException

class AccountIDConverter : AbstractBeanField<String, String>() {
    @Throws(CsvDataTypeMismatchException::class, CsvConstraintViolationException::class)
    override fun convert(value: String?): String? {
        if (value.isNullOrEmpty()) {
            return null
        }
        // Remove any currency symbols and commas
        val sanitizedValue = value.replace(Regex("[\$,]"), "")
        return sanitizedValue
    }
}