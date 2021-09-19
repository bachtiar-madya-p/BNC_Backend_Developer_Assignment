package com.app.rest.model.savingacc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SavingAccount {

    @JsonProperty("details")
    private List<SavingAccountDetails> savingAccountDetails;

    public SavingAccount(List<SavingAccountDetails> savingAccountDetails) {
        this.savingAccountDetails = savingAccountDetails;
    }

    public List<SavingAccountDetails> getSavingAccountDetails() {
        return savingAccountDetails;
    }

    public void setSavingAccountDetails(List<SavingAccountDetails> savingAccountDetails) {
        this.savingAccountDetails = savingAccountDetails;
    }
}
