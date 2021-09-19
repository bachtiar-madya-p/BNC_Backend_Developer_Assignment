package com.app.rest.validator;

import com.app.rest.model.User;

public class UserValidator extends BaseValidator {

    public UserValidator() {
    }

    public boolean validateRegistration(User request) {
        return notNull(request) && validate(request.getUsername()) && validate(request.getFullname())
                && validate(request.getEmail()) && validate(request.getPhone()) && validate(request.getPassword());
    }

    public boolean validateLogin(User request) {
        return notNull(request) && validate(request.getEmail()) && validate(request.getPassword());
    }
}
