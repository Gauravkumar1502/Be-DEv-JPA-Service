package dev.gaurav.testjpa.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

/*
    CREATE TABLE friends (
        user_id VARCHAR(50) NOT NULL check (user_id <> friend_id),
        friend_id VARCHAR(50) NOT NULL,
        PRIMARY KEY (user_id, friend_id),
        FOREIGN KEY (user_id) REFERENCES Users(username),
        FOREIGN KEY (friend_id) REFERENCES Users(username)
    );
    ADD CONSTRAINT no_self_friend CHECK (user_id <> friend_id);
 */
@Entity
@Table(name = "friends")
@Data
public class Friend {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "username")
        private User user;

        @ManyToOne
        @JoinColumn(name = "friend_id", nullable = false, referencedColumnName = "username")
        private User friend;

        @Enumerated(EnumType.STRING)
        @Column(name = "status", nullable = false)
        private FriendStatus status;

        @Column(name = "became_friends_at", columnDefinition = "timestamp")
        private Timestamp becameFriendsAt;

        public enum FriendStatus {
                PENDING("Pending"), ACCEPTED("Accepted");
                private final String value;
                FriendStatus(String value) {
                        this.value = value;
                }
                public String getValue() {
                        return value;
                }
        }
}