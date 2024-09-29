package org.fletcher.utils

import com.opencsv.bean.AbstractBeanField

class BigDecimalConverter extends AbstractBeanField<BigDecimal, String> {

    @Override
    protected BigDecimal convert(String value) {
        if (value == null || value.isEmpty()) {
            return null
        }
        // Remove any currency symbols and commas
        String sanitizedValue = value.replaceAll("[\$,]", "")
        return new BigDecimal(sanitizedValue)
    }
}