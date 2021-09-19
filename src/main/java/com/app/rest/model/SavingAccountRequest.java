package com.app.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SavingAccountRequest {

    @JsonProperty("user-id")
    private String userId;

    @JsonProperty("purpose")
    private String purpose;

    @JsonProperty("tenor")
    private int tenor;

    @JsonProperty("first_deposit_amount")
    private double firstDepositAmount;

    @JsonProperty("monthly_deposit_amount")
    private double monthlyDepositAmount;

    private String createDt;

    public SavingAccountRequest() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public int getTenor() {
        return tenor;
    }

    public void setTenor(int tenor) {
        this.tenor = tenor;
    }

    public double getFirstDepositAmount() {
        return firstDepositAmount;
    }

    public void setFirstDepositAmount(double firstDepositAmount) {
        this.firstDepositAmount = firstDepositAmount;
    }

    public double getMonthlyDepositAmount() {
        return monthlyDepositAmount;
    }

    public void setMonthlyDepositAmount(double monthlyDepositAmount) {
        this.monthlyDepositAmount = monthlyDepositAmount;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }
}

