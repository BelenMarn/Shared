package com.izertis.SimpleShared.core.domain;

public class Balance {
    private Friend friend;
    private double balance;

    public Balance() {
    }

    public Balance(Friend friend, double balance) {
        this.friend = friend;
        this.balance = balance;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
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

        Balance balance1 = (Balance) o;
        return Double.compare(balance, balance1.balance) == 0 && friend.equals(balance1.friend);
    }

    @Override
    public int hashCode() {
        int result = friend.hashCode();
        result = 31 * result + Double.hashCode(balance);
        return result;
    }
}
