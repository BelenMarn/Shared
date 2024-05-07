package com.izertis.SimpleShared.secondaryAdapter.repository;

import com.izertis.SimpleShared.core.domain.Friend;
import com.izertis.SimpleShared.core.domain.exception.EmptyFriendListException;
import com.izertis.SimpleShared.core.domain.exception.FriendNotFoundException;

import java.util.List;

public interface FriendRepository {
    List<Friend> findAll() throws EmptyFriendListException;
    void save(Friend friend);
    void update(Long id, String name);
    Friend findFriendById(long idFriend) throws FriendNotFoundException;
}
