package dev.gaurav.testjpa.models;

import jakarta.persistence.*;
import lombok.Data;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.List;
import java.util.Set;

/*
CREATE TABLE Users (
    username VARCHAR(50) PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_salt VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    gender enum('Male', 'Female', 'Other'),
    location VARCHAR(100),
    birthday DATE,
    summary text,
    website VARCHAR(255),
    github VARCHAR(255),
    linkedin VARCHAR(255),
    twitter VARCHAR(255),
    skills TEXT,
    avatar_url VARCHAR(512),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_login TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
*/
@Entity
@Table(name = "Users")
@Data
public class User {
        @Id
        @Column(name = "username", nullable = false, columnDefinition = "varchar(50)")
        String username;
        @Column(name = "email", nullable = false, unique = true, columnDefinition = "varchar(100)")
        String email;
        @Column(name = "password_salt", nullable = false, columnDefinition = "bytea")
        byte[] passwordSalt;
        @Column(name = "password_hash", nullable = false, columnDefinition = "bytea")
        byte[] passwordHash;
        @Column(name = "name", nullable = false, columnDefinition = "varchar(100)")
        String name;
        @Enumerated(EnumType.STRING)
        @Column(name = "gender", nullable = false)
        Gender gender;
        @Column(name = "location", columnDefinition = "varchar(100)")
        String location;
        @Column(name = "birthday", columnDefinition = "date")
        String birthday;
        @Column(name = "summary", columnDefinition = "text")
        String summary;
        @Column(name = "website_url", columnDefinition = "varchar(255)")
        String website;
        @Column(name = "github_url", columnDefinition = "varchar(255)")
        String github;
        @Column(name = "linkedin_url", columnDefinition = "varchar(255)")
        String linkedin;
        @Column(name = "twitter_url", columnDefinition = "varchar(255)")
        String twitter;
        @Column(name = "skills", columnDefinition = "text")
        String skills;
        @Column(name = "avatar_url", columnDefinition = "varchar(255)")
        String avatarUrl;
        @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default current_timestamp")
        Timestamp createdAt;
        @Column(name = "updated_at", columnDefinition = "timestamp default current_timestamp")
        Timestamp updatedAt;
        @Column(name = "last_login", columnDefinition = "timestamp default current_timestamp")
        Timestamp lastLogin;
        public enum Gender {
            MALE("Male"), FEMALE("Female"), PREFER_NOT_TO_SAY("Prefer not to say"), OTHER("Other");
            private final String value;
            Gender(String value) {
                this.value = value;
            }
            public String getValue() {
                return value;
            }
        }
        @PrePersist
        public void createdAt() {
            this.createdAt = new Timestamp(System.currentTimeMillis());
        }
}