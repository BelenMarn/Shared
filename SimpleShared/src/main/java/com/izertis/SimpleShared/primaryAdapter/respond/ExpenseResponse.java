package com.izertis.SimpleShared.primaryAdapter.respond;

public class ExpenseResponse {
    private long idExpense;
    private long idFriend;
    private double amount;
    private String description;
    private String expenseDate;

    public ExpenseResponse() {
    }

    public ExpenseResponse(long idExpense, long idFriend, double amount, String description, String expenseDate) {
        this.idExpense = idExpense;
        this.idFriend = idFriend;
        this.amount = amount;
        this.description = description;
        this.expenseDate = expenseDate;
    }

    public long getIdExpense() {
        return idExpense;
    }

    public void setIdExpense(long idExpense) {
        this.idExpense = idExpense;
    }

    public long getIdFriend() {
        return idFriend;
    }

    public void setIdFriend(long idFriend) {
        this.idFriend = idFriend;
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

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpenseResponse that = (ExpenseResponse) o;
        return idExpense == that.idExpense && idFriend == that.idFriend && Double.compare(amount, that.amount) == 0 && description.equals(that.description) && expenseDate.equals(that.expenseDate);
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(idExpense);
        result = 31 * result + Long.hashCode(idFriend);
        result = 31 * result + Double.hashCode(amount);
        result = 31 * result + description.hashCode();
        result = 31 * result + expenseDate.hashCode();
        return result;
    }
}
