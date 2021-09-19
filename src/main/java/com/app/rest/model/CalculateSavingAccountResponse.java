package com.app.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CalculateSavingAccountResponse {

    @JsonProperty("purpose")
    private String purpose;

    @JsonProperty("tenor")
    private int tenor;

    @JsonProperty("first_deposit_amount")
    private double firstDepositAmount;

    @JsonProperty("monthly_deposit_amount")
    private double monthlyDepositAmount;

    @JsonProperty("grand_total")
    private double grandTotal;

    @JsonProperty("estimated-final-balance ")
    private double calculation;

    public CalculateSavingAccountResponse() {
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

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public double getCalculation() {
        return calculation;
    }

    public void setCalculation(double calculation) {
        this.calculation = calculation;
    }
}
