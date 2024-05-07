package com.izertis.SimpleShared.core.domain;

import java.util.Objects;

public class Friend {
    private long idFriend;
    private String name;

    public Friend() {
    }

    public Friend(long idFriend, String name) {
        this.idFriend = idFriend;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Friend friend = (Friend) o;
        return idFriend == friend.idFriend && Objects.equals(name, friend.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFriend, name);
    }
}
