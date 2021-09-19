package com.app.rest.validator;

import com.app.rest.model.SavingAccountRequest;

public class SavingAccountValidator extends BaseValidator{

    public SavingAccountValidator() {
    }

    public boolean validate(SavingAccountRequest request) {
        return notNull(request) && validate(request.getUserId()) && validate(request.getPurpose())
                && validate(String.valueOf(request.getTenor())) && validate(String.valueOf(request.getFirstDepositAmount()))
                && validate(String.valueOf(request.getMonthlyDepositAmount()));
    }

    public boolean validateCalculation(SavingAccountRequest request) {
        return notNull(request) && validate(request.getPurpose())
                && validate(String.valueOf(request.getTenor())) && validate(String.valueOf(request.getFirstDepositAmount()))
                && validate(String.valueOf(request.getMonthlyDepositAmount()));
    }
}
