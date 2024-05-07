package com.izertis.SimpleShared.secondaryAdapter.entity;

import java.util.Objects;

public class FriendEntity {
    private long idFriend;
    private String name;

    public FriendEntity(long idFriend, String name) {
        this.idFriend = idFriend;
        this.name = name;
    }

    public FriendEntity() {

    }

    public String getName() {
        return name;
    }

    public long getIdFriend() {
        return idFriend;
    }

    public void setIdFriend(long idFriend) {
        this.idFriend = idFriend;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendEntity that = (FriendEntity) o;
        return idFriend == that.idFriend && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFriend, name);
    }
}

