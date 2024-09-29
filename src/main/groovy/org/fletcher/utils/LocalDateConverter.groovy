package org.fletcher.utils

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvConstraintViolationException
import com.opencsv.exceptions.CsvDataTypeMismatchException
import org.apache.commons.beanutils.ConversionException

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class LocalDateConverter extends AbstractBeanField<LocalDate, String> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy")


    @Override
    protected LocalDate convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        if (value == null) {
            return null
        }
        if (value instanceof LocalDate) {
            return value
        }
        try {
            return LocalDate.parse(value.toString(), formatter)
        } catch (DateTimeParseException e) {
            throw new ConversionException("Can't convert value '" + value + "' to type " + type, e)
        }
    }
}