package com.izertis.SimpleShared.secondaryAdapter.entity;

public class BalanceEntity {
    private long idFriend;
    private String name;
    private double balance;

    public BalanceEntity() {
    }

    public BalanceEntity(long idFriend, String name, double balance) {
        this.idFriend = idFriend;
        this.name = name;
        this.balance = balance;
    }

    public long getIdFriend() {
        return idFriend;
    }

    public void setIdFriend(long idFriend) {
        this.idFriend = idFriend;
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

        BalanceEntity that = (BalanceEntity) o;
        return idFriend == that.idFriend && Double.compare(balance, that.balance) == 0 && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(idFriend);
        result = 31 * result + name.hashCode();
        result = 31 * result + Double.hashCode(balance);
        return result;
    }
}
