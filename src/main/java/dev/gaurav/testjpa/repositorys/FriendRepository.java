package dev.gaurav.testjpa.repositorys;

import dev.gaurav.testjpa.models.Friend;
import dev.gaurav.testjpa.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findFriendByUserAndStatus(User user, Friend.FriendStatus status);
    List<Friend> findFriendByFriendAndStatus(User friend, Friend.FriendStatus status);
    boolean existsByUserAndFriend(User user, User friend);
    Optional<Friend> findFriendByUserAndFriend(User user, User friend);
    @Modifying
    @Transactional
    @Query(value = "update Friends f set f.status = ?1 where f.id = ?2", nativeQuery = true)
    void updateStatus(Friend.FriendStatus status, Long id);
}
