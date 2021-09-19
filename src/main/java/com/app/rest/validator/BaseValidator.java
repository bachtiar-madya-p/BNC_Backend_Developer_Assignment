package com.app.rest.validator;

import com.app.util.helper.StringHelper;

public class BaseValidator {

    public BaseValidator() {
        //Empty Constructor
    }

    public boolean notNull(Object obj) {
        return null != obj;
    }

    public boolean validate(String str) {
        return StringHelper.isValid(str);
    }

    public boolean validate(String... strs) {
        for (String str : strs)
            if (!StringHelper.isValid(str)) {
                return false;
            }
        return true;
    }
}
