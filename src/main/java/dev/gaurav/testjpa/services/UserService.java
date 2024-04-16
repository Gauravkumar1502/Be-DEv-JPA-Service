package dev.gaurav.testjpa.services;

import dev.gaurav.testjpa.DTO.LoginUserDTO;
import dev.gaurav.testjpa.DTO.FriendRequestDTO;
import dev.gaurav.testjpa.DTO.RegisterUserDTO;
import dev.gaurav.testjpa.exceptions.AlreadyExistsException;
import dev.gaurav.testjpa.exceptions.DoesNotExistException;
import dev.gaurav.testjpa.models.Friend;
import dev.gaurav.testjpa.models.User;
import dev.gaurav.testjpa.repositorys.FriendRepository;
import dev.gaurav.testjpa.repositorys.UserRepository;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 512;
    UserService(UserRepository userRepository, FriendRepository friendRepository) {
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
    }
    public User getUserByUsername(String username) {
        return userRepository.findById(username).orElseThrow(() -> new DoesNotExistException("User not found with username: " + username));
    }
    public User getUserByEmailAddress(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new DoesNotExistException("User not found with email: " + email));
    }
    public byte[] generatePasswordSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
    private byte[] generatePasswordHash(String password, byte[] passwordSalt) {
        try{
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), passwordSalt, ITERATIONS, KEY_LENGTH);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean saveUser(RegisterUserDTO registerUserRequest) {
        if(userRepository.existsById(registerUserRequest.username())) {
            throw new AlreadyExistsException("User already exists with username: " + registerUserRequest.username());
        }
        if(userRepository.existsByEmail(registerUserRequest.email())) {
            throw new AlreadyExistsException("User already exists with email: " + registerUserRequest.email());
        }
        try {
            User user = new User();
            user.setUsername(registerUserRequest.username());
            user.setEmail(registerUserRequest.email());
            user.setPasswordSalt(generatePasswordSalt());
            user.setPasswordHash(generatePasswordHash(registerUserRequest.password(), user.getPasswordSalt()));
            user.setName(registerUserRequest.name());
            user.setGender(registerUserRequest.gender());
            System.out.println(user);
            userRepository.save(user);
            return true;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while generating password salt");
        } catch (Exception e) {
            throw new RuntimeException("Error while saving user");
        }
    }
//    Login
    public boolean verifyPassword(LoginUserDTO loginUserRequest) {
        User user = getUserByEmailAddress(loginUserRequest.email());
        byte[] loginPasswordHash = generatePasswordHash(loginUserRequest.password(), user.getPasswordSalt());
        return Arrays.equals(user.getPasswordHash(), loginPasswordHash);
    }
    public boolean sendFriendRequest(String username, String friendUsername) {
        if (username.equals(friendUsername)) {
            throw new IllegalArgumentException("You cannot send friend request to yourself");
        }
        User user = this.getUserByUsername(username);
        User friend = this.getUserByUsername(friendUsername);
//        find relation between user and friend and vice versa
        Friend friendRelation = friendRepository.findFriendByUserAndFriend(user, friend).orElse(null);
        Friend friendRelationReverse = friendRepository.findFriendByUserAndFriend(friend, user).orElse(null);
        System.out.println("Friend relation: " + friendRelation);
        System.out.println("Friend relation reverse: " + friendRelationReverse);
        if(friendRelation != null) {
            switch (friendRelation.getStatus()) {
                case ACCEPTED -> throw new AlreadyExistsException("You are already friends with " + friendUsername);
                case PENDING -> throw new AlreadyExistsException("Friend request already sent to " + friendUsername);
            }
        }
        if(friendRelationReverse != null) {
            switch (friendRelationReverse.getStatus()) {
                case ACCEPTED -> throw new AlreadyExistsException("You are already friends with " + friendUsername);
                case PENDING -> throw new AlreadyExistsException(friendUsername + " has already sent you a friend request");
            }
        }
        Friend friendRequestEntity = new Friend();
        friendRequestEntity.setUser(user);
        friendRequestEntity.setFriend(friend);
        friendRequestEntity.setStatus(Friend.FriendStatus.PENDING);
        friendRepository.save(friendRequestEntity);
        return true;
    }
    public List<FriendRequestDTO> getPendingFriendRequests(String username) {
        User user = this.getUserByUsername(username);
        List<Friend> pendingFriendRequests = friendRepository.findFriendByFriendAndStatus(user, Friend.FriendStatus.PENDING);
        return pendingFriendRequests.stream()
                .map(friend -> new FriendRequestDTO(friend.getUser().getName(), friend.getUser().getUsername())).toList();
    }

    public Set<FriendRequestDTO> getFriends(String username) {
        User user = this.getUserByUsername(username);
        List<Friend> friendsRelation = friendRepository.findFriendByUserAndStatus(user, Friend.FriendStatus.ACCEPTED);
        System.out.println("Friends: " + friendsRelation);
        List<Friend> friendsRelationReverse = friendRepository.findFriendByFriendAndStatus(user, Friend.FriendStatus.ACCEPTED);
        System.out.println("Friends reverse: " + friendsRelationReverse);
        HashSet<FriendRequestDTO> friends = new HashSet<>();
        friendsRelation.forEach(friend -> friends.add(new FriendRequestDTO(friend.getFriend().getName(), friend.getFriend().getUsername())));
        friendsRelationReverse.forEach(friend -> friends.add(new FriendRequestDTO(friend.getUser().getName(), friend.getUser().getUsername())));
        return friends;
    }

    public boolean acceptFriendRequest(String username, String friendUsername) {
        return true;
    }
}
