package com.izertis.SimpleShared.primaryAdapter.respond;

public class FriendResponse {
    private long idFriend;
    private String name;

    public FriendResponse() {
    }

    public FriendResponse(long idFriend, String name) {
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

        FriendResponse that = (FriendResponse) o;
        return idFriend == that.idFriend && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(idFriend);
        result = 31 * result + name.hashCode();
        return result;
    }
}
