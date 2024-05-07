package com.izertis.SimpleShared.primaryAdapter.request;

public class ExpenseRequest {
    private double amount;
    private String description;

    public ExpenseRequest() {
    }

    public ExpenseRequest(double amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpenseRequest that = (ExpenseRequest) o;
        return Double.compare(amount, that.amount) == 0 && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(amount);
        result = 31 * result + description.hashCode();
        return result;
    }
}