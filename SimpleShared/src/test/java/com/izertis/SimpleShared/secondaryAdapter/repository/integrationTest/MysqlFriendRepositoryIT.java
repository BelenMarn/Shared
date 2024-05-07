package com.izertis.SimpleShared.secondaryAdapter.repository.integrationTest;

import com.izertis.SimpleShared.core.domain.Friend;
import com.izertis.SimpleShared.core.domain.exception.EmptyFriendListException;
import com.izertis.SimpleShared.core.domain.exception.FriendNotFoundException;
import com.izertis.SimpleShared.secondaryAdapter.repository.FriendRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MysqlFriendRepositoryIT {

    private final FriendRepository friendRepository;

    @Autowired
    public MysqlFriendRepositoryIT(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    @Test
    public void shouldFindAllFriends() throws EmptyFriendListException {
        assertThat(friendRepository.findAll().size()).isGreaterThan(0);
    }

    @Test
    public void shouldFindFriendById() throws FriendNotFoundException {
        long idFriend = 3;
        Friend expected = new Friend(idFriend, "Pedro");

        assertThat(friendRepository.findFriendById(idFriend)).isEqualTo(expected);
    }

    @Test
    public void shouldSaveNewFriend() throws FriendNotFoundException {
        long id = 005;
        Friend expected = new Friend(id,"Friend to Test");

        friendRepository.save(expected);

        assertThat(friendRepository.findFriendById(id)).isEqualTo(expected);
    }

    @Test
    public void shouldUpdateFriend() throws FriendNotFoundException {
        long idFriend = 1;
        String name = "Update";

        Friend expected = new Friend(idFriend, name);

        friendRepository.update(idFriend, name);

        assertThat(friendRepository.findFriendById(idFriend)).isEqualTo(expected);
    }
}
