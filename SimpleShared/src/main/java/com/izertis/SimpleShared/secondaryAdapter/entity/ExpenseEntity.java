package com.izertis.SimpleShared.secondaryAdapter.entity;

import com.izertis.SimpleShared.core.domain.Friend;
import com.izertis.SimpleShared.primaryAdapter.request.ExpenseRequest;

import java.util.Objects;

public class ExpenseEntity {
    private long idExpense;
    private long idFriend;
    private double amount;
    private String description;
    private String expenseDate;

    public ExpenseEntity() {
    }

    public ExpenseEntity(long idExpense, long idFriend, double amount, String description, String expenseDate) {
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
        ExpenseEntity that = (ExpenseEntity) o;
        return idExpense == that.idExpense && idFriend == that.idFriend && Double.compare(amount, that.amount) == 0 && Objects.equals(description, that.description) && Objects.equals(expenseDate, that.expenseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idExpense, idFriend, amount, description, expenseDate);
    }
}