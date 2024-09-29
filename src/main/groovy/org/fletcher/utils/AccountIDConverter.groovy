package org.fletcher.utils

import com.opencsv.bean.AbstractBeanField
import com.opencsv.exceptions.CsvConstraintViolationException
import com.opencsv.exceptions.CsvDataTypeMismatchException

class AccountIDConverter extends AbstractBeanField<String, String> {
    @Override
    protected String convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        {
            if (value == null || value.isEmpty()) {
                return null
            }
            // Remove any currency symbols and commas
            String sanitizedValue = value.replaceAll("[\$,]", "")
            return sanitizedValue
        }
    }
}
