package com.izertis.SimpleShared.secondaryAdapter.repository.unitTest;
import com.izertis.SimpleShared.core.domain.Friend;
import com.izertis.SimpleShared.core.domain.exception.EmptyFriendListException;
import com.izertis.SimpleShared.core.domain.exception.FriendNotFoundException;
import com.izertis.SimpleShared.secondaryAdapter.entity.FriendEntity;
import com.izertis.SimpleShared.secondaryAdapter.repository.MysqlFriendRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class MysqlFriendRepositoryTest {

    private final JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
    private final MysqlFriendRepository sut = new MysqlFriendRepository(jdbcTemplate);

    @Test
    public void shouldFindAllFriends() throws EmptyFriendListException {
        // GIVEN
        final List<FriendEntity> training = new ArrayList<>();
        training.add(new FriendEntity(1, "Juan"));
        training.add(new FriendEntity(2, "Maria"));

        when(jdbcTemplate.query(anyString(), isA(RowMapper.class)))
                .thenReturn(training);

        final List<Friend> expected = new ArrayList<>();
        expected.add(new Friend(1, "Juan"));
        expected.add(new Friend(2, "Maria"));

        // WHEN
        final List<Friend> result = sut.findAll();

        // THEN
        assertEquals(expected.size(), result.size());
        org.assertj.core.api.Assertions.assertThat(result).containsExactlyElementsOf(expected);
    }

    @Test
    public void shouldFindFriendById() throws FriendNotFoundException {
        //GIVEN:
        long idFriend = 1;
        String name = "Juan";

        when(jdbcTemplate.queryForObject(anyString(), eq(String.class), eq(idFriend)))
                .thenReturn(name);

        Friend expected = new Friend(idFriend, name);

        //WHEN:
        Friend result = sut.findFriendById(idFriend);

        //THEN:
        assertEquals(expected, result);
    }

    @Test
    public void shouldSaveFriend(){
        //GIVEN
        final Friend friendGiven = new Friend(1, "Juan");
        //WHEN
        sut.save(friendGiven);
        //THEN
        verify(jdbcTemplate, times(1)).update(anyString(),
                eq(friendGiven.getIdFriend()),
                eq(friendGiven.getName())
        );
    }

    @Test
    public void shouldUpdateFriend(){
        //GIVEN
        long id =1;
        String name = "Paco";

        //WHEN
        sut.update(id, name);
        //THEN
        verify(jdbcTemplate, times(1)).update(anyString(), eq(name), eq(id));
    }
}