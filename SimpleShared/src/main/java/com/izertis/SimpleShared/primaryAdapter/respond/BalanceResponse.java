package com.izertis.SimpleShared.primaryAdapter.respond;

public class BalanceResponse {
    private String name;
    private double balance;

    public BalanceResponse() {
    }

    public BalanceResponse(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BalanceResponse that = (BalanceResponse) o;
        return Double.compare(balance, that.balance) == 0 && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + Double.hashCode(balance);
        return result;
    }
}
