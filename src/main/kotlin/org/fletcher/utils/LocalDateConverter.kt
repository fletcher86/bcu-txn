package org.fletcher.utils

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvConstraintViolationException
import com.opencsv.exceptions.CsvDataTypeMismatchException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class LocalDateConverter : AbstractBeanField<LocalDate, String>() {

    private val formatter = DateTimeFormatter.ofPattern("MM/dd/yy")

    @Throws(CsvDataTypeMismatchException::class, CsvConstraintViolationException::class)
    override fun convert(value: String?): LocalDate? {
        if (value == null) {
            return null
        }
        try {
            return LocalDate.parse(value, formatter)
        } catch (e: DateTimeParseException) {
            throw CsvDataTypeMismatchException("Can't convert value '$value' to LocalDate").apply { initCause(e) }
        }
    }
}