package com.izertis.SimpleShared.core.domain;

public class Expense {
    private long idExpense;
    private Friend friend;
    private double amount;
    private String description;
    private String expenseDate;

    public Expense() {
    }

    public Expense(long idExpense, Friend friend, double amount, String description, String expenseDate) {
        this.idExpense = idExpense;
        this.friend = friend;
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

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
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

        Expense expense = (Expense) o;
        return idExpense == expense.idExpense && Double.compare(amount, expense.amount) == 0 && friend.equals(expense.friend) && description.equals(expense.description) && expenseDate.equals(expense.expenseDate);
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(idExpense);
        result = 31 * result + friend.hashCode();
        result = 31 * result + Double.hashCode(amount);
        result = 31 * result + description.hashCode();
        result = 31 * result + expenseDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "idExpense=" + idExpense +
                ", friend=" + friend +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", expenseDate='" + expenseDate + '\'' +
                '}';
    }
}