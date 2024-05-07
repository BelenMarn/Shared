package com.izertis.SimpleShared.primaryAdapter.request;

import java.util.Objects;

public class FriendRequest {
    private long idFriend;
    private String name;

    public FriendRequest(long idFriend, String name) {
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
        FriendRequest that = (FriendRequest) o;
        return idFriend == that.idFriend && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFriend, name);
    }
}
