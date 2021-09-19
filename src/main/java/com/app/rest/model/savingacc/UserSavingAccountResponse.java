package com.app.rest.model.savingacc;

import com.app.rest.model.ServiceResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSavingAccountResponse extends ServiceResponse {
    @JsonProperty("data")
    private List<SavingAccount> savingAccountList;

    public UserSavingAccountResponse()
    {

    }

    public UserSavingAccountResponse(List<SavingAccount> savingAccountList) {
        this.savingAccountList = savingAccountList;
    }

    public UserSavingAccountResponse(int status, String description, List<SavingAccount> savingAccountList) {
        super(status, description);
        this.savingAccountList = savingAccountList;
    }

    public List<SavingAccount> getSavingAccountList() {
        return savingAccountList;
    }

    public void setSavingAccountList(List<SavingAccount> savingAccountList) {
        this.savingAccountList = savingAccountList;
    }
}
