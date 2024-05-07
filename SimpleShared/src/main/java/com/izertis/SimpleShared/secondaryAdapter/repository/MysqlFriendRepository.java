package com.izertis.SimpleShared.secondaryAdapter.repository;
import com.izertis.SimpleShared.core.domain.Friend;
import com.izertis.SimpleShared.core.domain.exception.EmptyFriendListException;
import com.izertis.SimpleShared.core.domain.exception.FriendNotFoundException;
import com.izertis.SimpleShared.secondaryAdapter.entity.FriendEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MysqlFriendRepository implements FriendRepository {
    private final String SELECT_FRIENDS = "SELECT id_friend, name FROM friend";
    private final String SELECT_FRIEND_BYID = "SELECT name FROM friend WHERE id_friend = ?";
    private final String INSERT_FRIEND = "INSERT INTO friend(id_friend, name) VALUES(?, ?)";
    private final String UPDATE_FRIEND = "UPDATE friend SET name = ? WHERE id_friend = ?";

    private JdbcTemplate template;
    public MysqlFriendRepository(JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
    }

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Friend> findAll() throws EmptyFriendListException {
        RowMapper<FriendEntity> mapper = new RowMapper<FriendEntity>() {
            @Override
            //Se ejecuta tantas veces como resultados existan.
            public FriendEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                FriendEntity f = new FriendEntity();
                f.setIdFriend(rs.getLong("id_friend"));
                f.setName(rs.getString("name"));
                return f;
            }
        };
        //Hay que convertir el resultset en un mapper.
        List<FriendEntity> friends = template.query(SELECT_FRIENDS, mapper);

        if(!friends.isEmpty()){
            return toFriendsDomain(friends);
        }else{
            throw new EmptyFriendListException("Empty friend list");
        }
    }

    @Override
    public Friend findFriendById(long idFriend) throws FriendNotFoundException {
        try {

            String name = template.queryForObject(SELECT_FRIEND_BYID, String.class, idFriend);

            if (name == null) {
                throw new FriendNotFoundException("Friend not found");
            }

            FriendEntity friendEntity = new FriendEntity();
            friendEntity.setIdFriend(idFriend);
            friendEntity.setName(name);
            return toFriendDomain(friendEntity);

        } catch (EmptyResultDataAccessException e) {
            throw new FriendNotFoundException("Friend not found");
        }
    }

    @Override
    public void save(Friend friend) {
        FriendEntity friendEntity = toFriendEntity(friend);

        template.update(INSERT_FRIEND, friendEntity.getIdFriend(), friendEntity.getName());
    }

    @Override
    public void update(Long id, String name) {
        template.update(UPDATE_FRIEND, name, id);
    }


    private FriendEntity toFriendEntity(Friend friend) {
        return new FriendEntity(friend.getIdFriend(), friend.getName());
    }

    private Friend toFriendDomain(FriendEntity friendEntity) {
        return new Friend(friendEntity.getIdFriend(), friendEntity.getName());
    }

    private List<Friend> toFriendsDomain(List<FriendEntity> friends){
        return friends.stream()
                .map(friend -> new Friend(
                        friend.getIdFriend(),
                        friend.getName()))
                .collect(Collectors.toList());
    }
}
